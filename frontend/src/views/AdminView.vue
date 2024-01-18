<template>
    <div class="items-stretch bg-white flex flex-col">
      <span
        class="justify-center items-stretch shadow-sm bg-white flex gap-5 p-5 max-md:max-w-full max-md:flex-wrap"
        ><div
          class="justify-center items-center bg-white flex aspect-square flex-col w-10 h-10"
        >
          <img
            loading="lazy"
            src="images/image1.jpg"
            class="aspect-square object-contain object-center w-full overflow-hidden"
          />
        </div>
        <div
          class="text-black text-3xl font-medium leading-9 self-center grow shrink basis-auto my-auto max-md:max-w-full"
        >
          관리자 페이지
        </div>
        <span
          class="justify-between items-stretch bg-white self-center flex gap-5 my-auto"
          ><div class="text-black text-base leading-6">위치검색</div>
          <div class="text-black text-base leading-6">게시판</div>
          <div class="text-black text-base leading-6">매칭</div>
          <div class="text-black text-base leading-6">캘린더</div></span
        >
        <div
          class="justify-center items-center bg-white flex aspect-square flex-col w-10 h-10"
        >
          <img
            loading="lazy"
            src="images/image2.jpg"
            class="aspect-square object-contain object-center w-full overflow-hidden"
          /></div
      ></span>
      <div class="w-full max-md:max-w-full">
        <div class="gap-5 flex max-md:flex-col max-md:items-stretch max-md:gap-0">
          <div
            class="flex flex-col items-stretch w-[15%] max-md:w-full max-md:ml-0"
          >
            <div
              class="items-stretch bg-black bg-opacity-10 flex w-full grow flex-col mx-auto pt-3 pb-12"
            >
              <span class="justify-between items-stretch flex gap-3 px-5 py-4"
                ><span
                  class="justify-center overflow-hidden text-black text-center text-ellipsis whitespace-nowrap text-base leading-6 items-center bg-black bg-opacity-10 aspect-square h-6 px-1.5 rounded-xl"
                  >😃</span
                >
                <div
                  class="text-black text-base font-medium leading-5 self-center grow whitespace-nowrap my-auto"
                >
                  회원 관리
                </div></span
              ><span
                class="justify-between items-stretch flex gap-3 mb-[795px] px-5 py-4 max-md:mb-10"
                ><span
                  class="justify-center overflow-hidden text-black text-center text-ellipsis whitespace-nowrap text-base leading-6 items-center bg-black bg-opacity-10 aspect-square h-6 px-1.5 rounded-xl"
                  >😃</span
                >
                <div
                  class="text-black text-base font-medium leading-5 self-center grow whitespace-nowrap my-auto"
                >
                  데이터
                </div></span
              >
            </div>
          </div>
          <div
            class="flex flex-col items-stretch w-[85%] ml-5 max-md:w-full max-md:ml-0"
          >
            <div class="flex grow flex-col items-stretch max-md:max-w-full">
              <div
                class="justify-center items-stretch flex flex-col px-16 py-12 max-md:max-w-full max-md:px-5"
              >
                <div
                  class="items-center flex flex-col justify-center my-2.5 px-16 max-md:max-w-full max-md:px-5"
                >
                  <span class="flex w-[520px] max-w-full flex-col items-stretch"
                    ><div
                      class="text-black text-center text-4xl font-bold leading-10 max-md:max-w-full"
                    >
                      회원 관리
                    </div>
                    <div
                      class="text-black text-center text-base leading-6 mt-6 max-md:max-w-full"
                    >
                      Manage your organization members
                    </div>
                    <span
                      class="overflow-hidden text-black text-opacity-50 text-ellipsis whitespace-nowrap text-sm leading-5 items-stretch self-center border bg-white justify-center mt-6 px-3 py-2 rounded-md border-solid border-black border-opacity-10"
                      >Search members</span
                    >
                    <div class="items-stretch self-center flex gap-3 mt-6">
                      <span
                        class="text-black text-base font-medium leading-6 whitespace-nowrap justify-center items-stretch border grow px-5 py-3 rounded-lg border-solid border-black max-md:pl-5"
                        >Export Members</span
                      ><span
                        class="text-white text-base font-medium leading-6 whitespace-nowrap justify-center items-stretch bg-black grow px-9 py-3 rounded-lg max-md:px-5"
                        >Add Member</span
                      >
                    </div>
                    <div
                      class="justify-center items-stretch self-center flex gap-3 mt-6"
                    >
                      <span
                        class="justify-center overflow-hidden text-black text-center text-ellipsis whitespace-nowrap text-sm leading-5 items-stretch bg-zinc-300 bg-opacity-50 grow p-2 rounded-md"
                        >All Members</span
                      ><span
                        class="justify-center overflow-hidden text-black text-center text-ellipsis whitespace-nowrap text-sm leading-5 items-stretch bg-zinc-300 bg-opacity-50 grow p-2 rounded-md"
                        >Active Members</span
                      ><span
                        class="justify-center overflow-hidden text-black text-center text-ellipsis whitespace-nowrap text-sm leading-5 items-stretch bg-zinc-300 bg-opacity-50 grow p-2 rounded-md"
                        >Inactive Members</span
                      >
                    </div></span
                  >
                </div>
              </div>
              <span
                class="justify-center items-center flex flex-col px-16 py-12 max-md:max-w-full max-md:px-5"
                ><div
                  class="text-black text-center text-4xl font-bold leading-10 mt-2.5 max-md:max-w-full"
                >
                  회원 통계
                </div>
                <div
                  class="text-black text-center text-base leading-6 mt-6 max-md:max-w-full"
                >
                  View data visualization of members
                </div>
                <div class="self-stretch mt-6 py-5 max-md:max-w-full">
                  <div
                    class="gap-5 flex max-md:flex-col max-md:items-stretch max-md:gap-0"
                  >
                    <div
                      class="flex flex-col items-stretch w-[33%] max-md:w-full max-md:ml-0"
                    >
                      <span
                        class="items-stretch border flex grow flex-col w-full p-4 rounded-md border-solid border-black border-opacity-10 max-md:mt-10"
                        ><div
                          class="overflow-hidden text-black text-opacity-50 text-ellipsis whitespace-nowrap text-base leading-6"
                        >
                          Total Members
                        </div>
                        <div
                          class="overflow-hidden text-black text-ellipsis whitespace-nowrap text-3xl font-medium leading-9 mt-1"
                        >
                          1000
                        </div></span
                      >
                    </div>
                    <div
                      class="flex flex-col items-stretch w-[33%] ml-5 max-md:w-full max-md:ml-0"
                    >
                      <span
                        class="items-stretch border flex grow flex-col w-full p-4 rounded-md border-solid border-black border-opacity-10 max-md:mt-10"
                        ><div
                          class="overflow-hidden text-black text-opacity-50 text-ellipsis whitespace-nowrap text-base leading-6"
                        >
                          Active Members
                        </div>
                        <div
                          class="overflow-hidden text-black text-ellipsis whitespace-nowrap text-3xl font-medium leading-9 mt-1"
                        >
                          800
                        </div></span
                      >
                    </div>
                    <div
                      class="flex flex-col items-stretch w-[33%] ml-5 max-md:w-full max-md:ml-0"
                    >
                      <span
                        class="items-stretch border flex grow flex-col w-full p-4 rounded-md border-solid border-black border-opacity-10 max-md:mt-10"
                        ><div
                          class="overflow-hidden text-black text-opacity-50 text-ellipsis whitespace-nowrap text-base leading-6"
                        >
                          Inactive Members
                        </div>
                        <div
                          class="overflow-hidden text-black text-ellipsis whitespace-nowrap text-3xl font-medium leading-9 mt-1"
                        >
                          200
                        </div></span
                      >
                    </div>
                  </div>
                </div>
                <span
                  class="items-stretch self-stretch border flex flex-col mt-6 mb-2.5 p-4 rounded-md border-solid border-black border-opacity-10 max-md:max-w-full"
                  ><div
                    class="text-black text-xl font-medium leading-7 max-md:max-w-full"
                  >
                    Member Status Distribution
                  </div>
                  <img
                    loading="lazy"
                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/46df34c05f527321dc692d87945683329dec8203c67f44bd18249444a35ad0b2?"
                    class="aspect-[3.56] object-contain object-center w-full overflow-hidden mt-3 max-md:max-w-full" /></span
              ></span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  