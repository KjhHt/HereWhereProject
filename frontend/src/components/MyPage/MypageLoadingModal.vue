<template>
    <!-- 모달 -->
    <!-- <div v-if="loadingModal">
        <img src="@/assets/vacations.gif" alt="Trip-moment" style="margin-top: 200px;" />
    </div> -->
    <div class="loader" >
        <div class="loader__trampoline"></div>
        <div class="loader__jumper">
        <div class="loader__jumper-face">
            <div class="loader__jumper-eyes"></div>
            <div class="loader__jumper-mouth"></div>
        </div>
        </div>
        <div class="loader__text">Loading...</div>
    </div>
    <!-- 모달 끝 -->
</template>

<script setup>

</script>

<style scope lang="scss">
@import url("https://fonts.googleapis.com/css2?family=Archivo+Black&display=swap");
$black: #000038;
$white: #fff;
$pink: #eb80b1;
$green: #80c0a1;
$light-grey: #bdc3c6;
$grey: #919ea3;
$dark-grey: #576078;

$animation-duration: 10s;
$jumper-animation-timing: cubic-bezier(0.52, -0.01, 0.14, 1);

.loader {
  //margin-top: 10rem;
  margin: auto;
  margin-top: 200px;
  $component-class: &;
  position: relative;
  &__trampoline {
    position: relative;
    width: 12rem;
    height: 4rem;
    border-radius: 50%;
    background: radial-gradient(ellipse at 50% 50%, $black, $dark-grey 35%);
    background-size: 200% 200%;
    background-position: 50% 50%;
    border: 0.2rem solid $light-grey;
    box-shadow: 0 1.2rem 1rem 0 rgba($black, 0.5);
    &:before,
    &:after {
      content: "";
      position: absolute;
      width: 0.2rem;
      height: 1rem;
      background-color: $light-grey;
    }
    &:before {
      bottom: 0;
      left: 0.8rem;
    }
    &:after {
      bottom: 0;
      right: 0.8rem;
    }
  }
  &__jumper {
    position: absolute;
    top: 0;
    left: 5rem;
    width: 2.5rem;
    height: 2.5rem;
    border-radius: 50%;
    background: radial-gradient(circle at 70% 70%, $pink, $black 500%);
    overflow: hidden;
  }
  &__jumper-face {
    width: 100%;
    height: 100%;
  }
  &__jumper-eyes {
    position: absolute;
    top: 35%;
    left: 20%;
    display: flex;
    justify-content: space-between;
    width: 60%;
    height: 0.3rem;
    &:before,
    &:after {
      content: "";
      width: 0.3rem;
      height: 0.3rem;
      border-radius: 50%;
      background: radial-gradient(
        circle at 70% 30%,
        $white 15%,
        $black 15%,
        $black
      );
    }
  }
  &__jumper-mouth {
    position: absolute;
    top: 60%;
    left: calc(35% - 0.1rem);
    width: 30%;
    height: 0.1rem;
    border-radius: 0 0 1rem 1rem;
    border: 0.1rem solid $black;
    border-top: none;
  }
  &__text {
    color: $black;
    text-align: center;
    text-transform: uppercase;
    padding-top: 2rem;
  }
  // Animation
  &__trampoline {
    @keyframes bounce-trampoline {
      5% {
        transform: translateY(0.1rem);
        box-shadow: 0 1.1rem 1rem 0 rgba($black, 0.5);
        background-position: 50% 45%;
      }
      10%,
      15% {
        transform: translateY(-0.1rem);
        box-shadow: 0 1.3rem 1rem 0 rgba($black, 0.5);
        background-position: 50% 50%;
      }
      25% {
        transform: translateY(0.1rem);
        box-shadow: 0 1.1rem 1rem 0 rgba($black, 0.5);
        background-position: 45% 45%;
      }
      30%,
      35% {
        transform: translateY(-0.1rem);
        box-shadow: 0 1.3rem 1rem 0 rgba($black, 0.5);
        background-position: 50% 50%;
      }
      45% {
        transform: translateY(0.1rem);
        box-shadow: 0 1.1rem 1rem 0 rgba($black, 0.5);
        background-position: 60% 45%;
      }
      50%,
      55% {
        transform: translateY(-0.1rem);
        box-shadow: 0 1.3rem 1rem 0 rgba($black, 0.5);
        background-position: 50% 50%;
      }
      65% {
        transform: translateY(0.1rem);
        box-shadow: 0 1.1rem 1rem 0 rgba($black, 0.5);
        background-position: 40% 40%;
      }
      70%,
      75% {
        transform: translateY(-0.1rem);
        box-shadow: 0 1.3rem 1rem 0 rgba($black, 0.5);
        background-position: 50% 50%;
      }
      85% {
        transform: translateY(0.1rem);
        box-shadow: 0 1.1rem 1rem 0 rgba($black, 0.5);
        background-position: 60% 35%;
      }
      95% {
        transform: translateY(0.1rem);
        box-shadow: 0 1.1rem 1rem 0 rgba($black, 0.5);
        background-position: 48% 50%;
      }
      100% {
        transform: none;
        background-position: 50% 50%;
      }
    }
    animation: bounce-trampoline;
    animation-duration: $animation-duration;
    animation-timing-function: $jumper-animation-timing;
    animation-iteration-count: infinite;
  }
  &__jumper {
    @keyframes bounce-jumper {
      5% {
        transform: translateY(0.5rem);
        transform-origin: 50% calc(50% - 0.5rem);
      }
      15% {
        transform: translate(0.5rem, -5rem) rotate(20deg);
        transform-origin: calc(50% - 0.5rem) calc(50% + 5rem);
      }
      23% {
        transform: translate(1rem, 0.5rem) rotate(30deg);
        transform-origin: calc(50% - 1rem) calc(50% + 0.5rem);
      }
      35% {
        transform: translate(-1rem, -8rem) rotate(-10deg);
        transform-origin: calc(50% + 1rem) calc(50% + 5rem);
      }
      43% {
        transform: translate(-2rem, 0.5rem) rotate(-20deg);
        transform-origin: calc(50% + 2rem) calc(50% - 0.5rem);
      }
      55% {
        transform: translate(1.5rem, -6rem) rotate(10deg);
        transform-origin: calc(50% - 4rem) calc(50% + 6rem);
      }
      63% {
        transform: translate(3.5rem, 0.5rem) rotate(10deg);
        transform-origin: calc(50% - 3.5rem) calc(50% - 0.5rem);
      }
      75% {
        transform: translate(0, -8rem) rotate(-20deg);
        transform-origin: 50% calc(50% + 10rem);
      }
      83% {
        transform: translate(-3rem, -0.5rem) rotate(-30deg);
        transform-origin: calc(50% + 3rem) calc(50% + 0.5rem);
      }
      95% {
        transform: translate(1rem, 0) rotate(20deg);
        transform-origin: calc(50% - 1rem) 50%;
      }
      100% {
        transform: none;
        transform-origin: 50% 50%;
      }
    }
    animation: bounce-jumper;
    animation-duration: $animation-duration;
    animation-timing-function: $jumper-animation-timing;
    animation-iteration-count: infinite;
  }
  &__jumper-face {
    @keyframes bounce-face {
      5% {
        transform: translateY(0.5rem);
      }
      15% {
        transform: translate(0.5rem, -3rem) rotate(20deg);
      }
      24% {
        transform: translate(1rem, 0.5rem) rotate(30deg);
      }
      35% {
        transform: translate(-1rem, -3rem) rotate(-10deg);
      }
      44% {
        transform: translate(-0.5rem, 0.5rem) rotate(-20deg);
      }
      55% {
        transform: translate(0, -0.5rem) rotate(10deg);
      }
      64% {
        transform: translate(0rem, 0.5rem) rotate(10deg);
      }
      75% {
        transform: translate(0, -1rem) rotate(-20deg);
      }
      84% {
        transform: translate(-0.5rem, 0.5rem) rotate(-30deg);
      }
      95% {
        transform: translate(0.5rem, -0.5rem) rotate(-10deg);
      }
      100% {
        transform: none;
      }
    }
    animation: bounce-face;
    animation-duration: $animation-duration;
    animation-timing-function: ease;
    animation-iteration-count: infinite;
  }
  &__jumper-mouth {
    @keyframes bounce-mouth {
      15%,
      75% {
        height: 0.4rem;
        border-radius: 0 0 2rem 2rem;
      }
    }
    animation: bounce-mouth $animation-duration;
    animation-duration: $animation-duration;
    animation-timing-function: ease;
    animation-iteration-count: infinite;
  }
  &__text {
    @keyframes bounce-text {
      5% {
        transform: translateY(0);
      }
      15% {
        transform: translateY(-0.25rem);
      }
      22% {
        transform: translateY(0);
      }
      35% {
        transform: translateY(-0.25rem);
      }
      42% {
        transform: translateY(0);
      }
      55% {
        transform: translateY(-0.25rem);
      }
      62% {
        transform: translateY(0);
      }
      75% {
        transform: translateY(-0.25rem);
      }
      82% {
        transform: translateY(0);
      }
      95% {
        transform: translateY(0);
      }
      100% {
        transform: none;
      }
    }
    animation: bounce-text;
    animation-duration: $animation-duration;
    animation-timing-function: ease;
    animation-iteration-count: infinite;
  }
}
</style>