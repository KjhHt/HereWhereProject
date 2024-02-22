<template>
    <div v-if=props.loading class="spinner-border m-5 text-primary" role="status">
        <span class="visually-hidden">Loading...</span>
    </div>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
loading: Boolean,
});

</script>

<style scoped>
.loading-overlay {
    color: black;
}
</style>
