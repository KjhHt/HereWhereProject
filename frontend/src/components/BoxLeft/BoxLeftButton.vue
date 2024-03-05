<template>
    <span>
      <button
        type="button"
        class="image-gallery-left-nav left-nav-big"
        aria-label="Previous Slide"
        :disabled="props.currentIndex === 1"
        @click="moveImage(-1)">
      </button>
      <button
        type="button"
        class="image-gallery-right-nav right-nav-big"
        aria-label="Next Slide"
        :disabled="props.currentIndex === props.totalIndex"
        @click="moveImage(1)">
      </button>
    </span>
  </template>
<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  currentIndex: Number,
  totalIndex: Number
});

const emit = defineEmits(['moveImage']);

const moveImage = (direction) => {
  emit('moveImage', direction);
};
</script>
<style scope>
.hwNqPL .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery .image-gallery-left-nav.image-gallery-overview-left-nav {
    width: 48px;
    height: 136px;
    display: flex;
    -webkit-box-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    align-items: center;
    left: 0px !important;
    background-image: none !important;
    background-color: rgba(0, 0, 0, 0.7) !important;
}
.hwNqPL .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-left-nav[disabled] {
    background: url(https://ak-d.tripcdn.com/images/0AS29120008am3x2cC200.png) 0% 0% / 100% 100% no-repeat;
}

.hwNqPL .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery .image-gallery-left-nav {
    height: 64px;
    width: 64px;
    box-shadow: none;
    transform: translateY(-50%) scale(1);
    left: 16px !important;
    background: url(https://ak-d.tripcdn.com/images/0AS4s120008am3vd9B541.png) 0% 0% / 100% 100% no-repeat transparent;
    padding: 0px;
    border-width: initial;
    border-style: none;
    border-color: initial;
    border-image: initial;
    border-radius: 0px;
}

.hwNqPL .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery .left-nav-big {
    left: 50px !important;
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .left-nav-big {
    width: 50px;
    left: 90px !important;
}
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-left-nav, .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-right-nav {
    -webkit-transform: translateY(-50%) scaleX(1.25);
    transform: translateY(-50%) scaleX(1.25);
}
}
@media screen and (min-width: 426px){
.image-gallery-left-nav[disabled], .image-gallery-right-nav[disabled] {
    cursor: not-allowed;
    /* opacity: 0.6; */
    pointer-events: none;
}
}
@media screen and (min-width: 426px){
.image-gallery-left-nav[disabled] {
    background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAyCAMAAAAKuilWAAAAeFBMVEUAAADQ0NDPz8/Q0NDU1NTT09PNzc3Nzc3MzMzNzc3R0dHNzc3Nzc3Nzc3Nzc3MzMzNzc3MzMzMzMzOzs7Ozs7U1NTa2trNzc3Nzc3Nzc3Nzc3Nzc3Pz8/t7e3Nzc3Nzc3Nzc3Nzc3MzMzNzc3Ozs7Nzc3MzMzMzMz9IaynAAAAJ3RSTlMAIx0YEQ7X9ufHFPDr0irj4N3OPzcKBsvCs2tdMQPavLiqn5ZNR+Iwu+ctAAAAz0lEQVQ4y83UyRKCMBAEUCABIcoWVBQUd/P/f6jpqhx7cmWurw+pzJKsuPJXNUmuR+fcwN1Y96+Zuqq9t5p6AU+ZZ/COe++9z5innfeCewtXzJvSe22o77xbzfwCr6jvt/Bc9hP1YeP9dmD+gd8jfj4yf8MfMb8yV3j/k3oyy44AHkhLl7HEIH8Cvpl8I2kEbXU8gWbL4zRq5mEgreGJVBxZJL7i0GNtQkJevCUTEouwmigVljt2PoSEQaJseEJbJEzkyLlJOpMVzmDskK6lfhHUISzFa05CAAAAAElFTkSuQmCC) no-repeat center;
}
}
@media screen and (min-width: 426px){
.image-gallery-left-nav {
    left: 5px;
    background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAyCAMAAAAKuilWAAAAbFBMVEUAAACIiIiMjIyKioqLi4uNjY2IiIiHh4eHh4eHh4eIiIiRkZGOjo6IiIiIiIiIiIiYmJilpaWHh4eHh4eHh4eLi4uLi4uIiIiIiIiJiYmJiYmJiYmHh4eIiIiIiIiIiIiKioqHh4eIiIiHh4cb9ikJAAAAI3RSTlMA5REjHRjsx/PWzBQN3tE/CQXh2rM3KPnAa10uuaqflk0yR66IyNoAAADISURBVDjLzdTLEoMgDAVQRZAigq22ttW++f9/7CRdsLphpiuzPXdGgSTVhqt/2qvkqkspDdg9eVqgm0heK+iOvUGuJ/IReyAPGnkzkk/Ya3JnkM/s0SNfW/JOIX+zW+inn/fQd+QX6AP7bS/7EfrnzH5A/mK//+2Gv/+AXi3skDnAPwhL1eCI6JLwNUuJNj+E+NRCYoXNgNsJN2Q5IQ+FM0IilBI65MGSRjcICePwcMvrIZePnJilFcYJX0pIe1BZWoPFRbqV+gIaTh2u/NVaXQAAAABJRU5ErkJggg==) no-repeat center;
}
}
@media screen and (min-width: 426px){
.image-gallery-left-nav, .image-gallery-right-nav {
    color: #fff;
    font-size: 55px;
    padding: 50px 15px;
    top: 50%;
    -webkit-transform: translateY(-50%);
    transform: translateY(-50%);
}
}
@media screen and (min-width: 426px){
.image-gallery-fullscreen-button, .image-gallery-play-button, .image-gallery-left-nav, .image-gallery-right-nav {
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    background-color: transparent;
    border: 0;
    cursor: pointer;
    outline: none;
    position: absolute;
    z-index: 4;
}
}
button {
    outline: none;
}

button:disabled {
    background-color: -internal-light-dark(rgba(239, 239, 239, 0.3), rgba(19, 1, 1, 0.3));
    color: -internal-light-dark(rgba(16, 16, 16, 0.3), rgba(255, 255, 255, 0.3));
    border-color: -internal-light-dark(rgba(118, 118, 118, 0.3), rgba(195, 195, 195, 0.3));
}
/*우측 버튼*/
.hwNqPL .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery .image-gallery-right-nav {
    height: 64px;
    width: 64px;
    box-shadow: none;
    transform: translateY(-50%) scale(1);
    right: 16px !important;
    background: url(https://ak-d.tripcdn.com/images/0AS6t120008am43i619BF.png) 0% 0% / 100% 100% no-repeat transparent;
    padding: 0px;
    border-width: initial;
    border-style: none;
    border-color: initial;
    border-image: initial;
    border-radius: 0px;
}
.hwNqPL .gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery .right-nav-big {
    right: 50px !important;
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .right-nav-big {
    width: 60px;
    right: 90px !important;
}
}
@media screen and (min-width: 426px){
.gl-cpt_imagallery .photo-list-warp .photo-list-imgs-list .image-gallery-right-nav {
    right: 8px;
}
}
@media screen and (min-width: 426px){
.image-gallery-right-nav {
    right: 1px;
    background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAyCAMAAAAKuilWAAAAbFBMVEUAAACIiIiTk5ORkZGHh4eHh4eKioqLi4uMjIyIiIiHh4efn5+Hh4eLi4uHh4eJiYmIiIiIiIiIiIiIiIiJiYmIiIiHh4eJiYmJiYmIiIiIiIiIiIiKioqKioqJiYmNjY2IiIiJiYmIiIiHh4cLsrptAAAAI3RSTlMA5QwT38kjHRjs2QbzELk81dLOwzT5sEQupJpiXE0pJ5RwbV8qaTEAAAC/SURBVDjLzdTLFoIwDARQoBQqUoH6ABFF7f//o6ZdsMqEw4pscxc8MpPsZb56zNC+8d5rC8DD/8cpHqiURGd4UQTRIpGTqEpelBWJXBYFL0xLIgVCdUG8gHAkDgMvrA7iwovsvE7UDRAnSRyj6IG4kbjK4gnEfZ2oTcLPSGKSHmMSXjW18sfCe/mH4b1wEsPWvXLxLIWzncHhw2gYGB05fOUSXxRvs7Ug5lgxCleQU6jEcM31tIdF+dHvLNnx/AA97h0eaRSCeQAAAABJRU5ErkJggg==) no-repeat center;
}
}

</style>