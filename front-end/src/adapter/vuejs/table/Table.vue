<template>
    <div class="container">
        <div class="table-container">
            <table class="table">
            <thead>
                <tr>
                <slot name="thead"></slot>
                </tr>
            </thead>
            <tbody>
                <slot name="tbody"></slot>
            </tbody>
            </table>
        </div>
    </div>
</template>

<script lang="ts">
export default {
}

</script>
  
<style>
    .container {
    display: flex;
    flex-direction: column;
    }

    .table-container {
    overflow-x: auto;
    padding: 0.5rem;
    }

    .table {
    width: 100%;
    border-collapse: collapse;
    vertical-align: middle;
    }

    thead {
    background-color: #f9fafb;
    }

    tbody {
    background-color: #ffffff;
    }

    tr {
    border-top: 1px solid #edf2f7;
    }

</style>