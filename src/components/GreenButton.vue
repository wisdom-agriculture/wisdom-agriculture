<template>
    <a-button>
        <slot></slot>
    </a-button>
</template>
<style lang="less" scoped>
button {
    width: 100%;
    height: 60px;
    color: #fff;
    font-size: 18px;
    font-weight: 600;
    margin-top: 16px;
    border-radius: 30px;
    border-color: rgb(var(--green-6));
    background: rgb(var(--green-6));
}

.ant-btn:hover,
.ant-btn:focus,
.ant-btn:active {
    text-decoration: none;
    background: rgb(var(--green-6));
    color: #fff;
    border-color: rgb(var(--green-6));
}
</style>