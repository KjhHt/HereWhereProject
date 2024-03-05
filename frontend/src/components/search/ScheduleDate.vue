<template>
    <div>
        <!-- Modal -->
        <div class="modal fade" id="scheduleDateModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">
                        일정 추가
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    ...
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" @click="addSchedule">Save changes</button>
                </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script setup>
    import { defineEmits } from 'vue'
    
    const emit= defineEmits(['addSchedule'])

    const addSchedule=()=>{
        emit('addSchedule','스케쥴')
    }
    
</script>

<style scoped>

</style>