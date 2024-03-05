import base64
from google.cloud import vision
import aiohttp
import aiofiles
import os
import asyncio
from PIL import Image,ImageFilter,ImageDraw
import io
from typing import MutableSequence

def get_crop_hint(path: str) -> MutableSequence[vision.Vertex]:
    """Detect crop hints on a single image and return the first result.

    Args:
        path: path to the image file.

    Returns:
        The vertices for the bounding polygon.
    """
    client = vision.ImageAnnotatorClient()

    with open(path, "rb") as image_file:
        content = image_file.read()

    image = vision.Image(content=content)

    crop_hints_params = vision.CropHintsParams(aspect_ratios=[2])
    image_context = vision.ImageContext(crop_hints_params=crop_hints_params)

    response = client.crop_hints(image=image, image_context=image_context)
    hints = response.crop_hints_annotation.crop_hints

    # Get bounds for the first crop hint using an aspect ratio of 1.77.
    vertices = hints[0].bounding_poly.vertices

    return vertices

def draw_hint(image_file: str) -> None:
    """Draw a border around the image using the hints in the vector list.

    Args:
        image_file: path to the image file.
    """
    vects = get_crop_hint(image_file)

    im = Image.open(image_file)
    draw = ImageDraw.Draw(im)
    draw.polygon(
        [
            vects[0].x,
            vects[0].y,
            vects[1].x,
            vects[1].y,
            vects[2].x,
            vects[2].y,
            vects[3].x,
            vects[3].y,
        ],
        None,
        "red",
    )
    im.save("output-hint.jpg", "JPEG")
    print("Saved new image to output-hint.jpg")
def annotate(path: str) -> vision.WebDetection:
    client = vision.ImageAnnotatorClient()

    if path.startswith("http") or path.startswith("gs:"):
        image = vision.Image()
        image.source.image_uri = path
    elif path.startswith("data"):
        base64_data = path.split(',')[1]
        image_data = base64.b64decode(base64_data)
        image = vision.Image(content=image_data)
    else:
        content = Image.open(path)
        if content.mode == 'RGBA':
            content=content.convert('RGB')
        sharpened = content.filter(ImageFilter.SHARPEN)
        byte_stream = io.BytesIO()
        sharpened.save(byte_stream, format='JPEG')
        byte_stream.seek(0)
        image = vision.Image(content=byte_stream.read())

    web_detection = client.web_detection(image=image).web_detection
    return web_detection

def report(annotations: vision.WebDetection) -> None:
    """Prints detected features in the provided web annotations.

    Args:
        annotations: The web annotations (WebDetection object) from which
        the features should be parsed and printed.
    """
    urls = []
    descriptions = []
    if annotations.pages_with_matching_images:
        print(
            f"\n{len(annotations.pages_with_matching_images)} Pages with matching images retrieved"
        )
        for page in annotations.pages_with_matching_images:
            print(f"Url   : {page.url}")



    if annotations.partial_matching_images:
        print(f"\n{len(annotations.partial_matching_images)} Partial Matches found: ")
        for image in annotations.partial_matching_images:
            print(f"Url  : {image.url}")

    if annotations.web_entities:
        print(f"\n{len(annotations.web_entities)} Web entities found: ")
        for entity in annotations.web_entities:
            print(f"Score      : {entity.score}")
            print(f"Description: {entity.description}")
            descriptions.append(entity.description)

    if annotations.full_matching_images:
        print(f"\n{len(annotations.full_matching_images)} Full Matches found: ")

        for image in annotations.full_matching_images:
            print(f"Url  : {image.url}")
            urls.append(image.url)
    return descriptions, urls

async def download_image(session, url, file_name):
    async with session.get(url) as response:
        if response.status == 200:
            f = await aiofiles.open(file_name, mode='wb')
            await f.write(await response.read())
            await f.close()

async def image_download(urls, descriptions):
    async with aiohttp.ClientSession() as session:
        tasks = []
        for index, url in enumerate(urls):
            file_name = os.path.join('upload', f'{descriptions[0]}_{index}.jpg')
            task = asyncio.ensure_future(download_image(session, url, file_name))
            tasks.append(task)
        await asyncio.gather(*tasks)