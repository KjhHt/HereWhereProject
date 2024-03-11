<template>
    <div v-if="isModalVisible" class="layerContainer_"  style="background: rgba(17, 17, 17, 0.5); position: fixed; inset: 0px; text-align: center; z-index: 9999;">
        <div class="LayerContainer-sc-16ecu2q-0 iwjgOX">
            
            <div class="loading-module ">

                    <img src="@/assets/blue.gif">

                <div class="loading-message">
                    번역중 입니다...
                </div>                
            </div>
        </div>
    </div>
</template>
<script setup>
import { inject } from 'vue';

let isModalVisible = inject('isModalVisible');
</script>
<style scope>
.layerContainer_ {
    background: rgba(17, 17, 17, 0.5);
    position: fixed;
    top: 0px; /* 원하는 위치로 조정 */
    left: 0px; /* 원하는 위치로 조정 */
    width: 650px; /* 원하는 너비로 조정 */
    height: 604.58px; /* 원하는 높이로 조정 */
    text-align: center;
    z-index: 9999;
}
.loading-module {
    padding: 24px;
    width: 610px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);

    border-radius: 5px;
    display: flex; 
    justify-content: center; 
    align-items: center;
    flex-direction: column;
}

.loading-message{
  color:white
}
</style>