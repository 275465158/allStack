<template>
    <div class="tags" v-if="showTags">
        <ul>
            <li class="tags-li" v-for="(item,index) in tagsList" :class="{'active': isActive(item.path)}" :key="index">
                <router-link :to="{path:item.fullPath, query: {works: 1}}" class="tags-li-title">
                    {{item.title}}
                </router-link>
                <span class="tags-li-icon" @click="closeTags(index)"><i class="el-icon-close"></i></span>
            </li>
        </ul>
        <div class="tags-close-box">
            <el-dropdown @command="handleTags">
                <el-button size="mini" type="primary">
                    标签选项<i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
                <el-dropdown-menu size="small" slot="dropdown">
                    <el-dropdown-item command="other">关闭其他</el-dropdown-item>
                    <el-dropdown-item command="all">关闭所有</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
    import Bus from './bus.js'
    export default {
        data() {
            return {
                tagsList: []
            }
        },
        methods: {
            isActive(path) {
                return path === this.$route.path;
            },
            // 关闭单个标签
            closeTags(index) {
                const delItem = this.tagsList.splice(index, 1)[0];
                const item = this.tagsList[index] ? this.tagsList[index] : this.tagsList[index - 1];
                if (item) {
                    delItem.path === this.$route.path && this.$router.push(item.fullPath);
                }else{
                    this.$router.push('/');
                }
            },
            // 关闭全部标签
            closeAll(){
                this.tagsList = [];
                this.$router.push('/');
            },
            // 关闭其他标签
            closeOther(){
                const curItem = this.tagsList.filter(item => {
                    return item.path === this.$route.path;
                })
                console.log(this.tagsList);
                this.tagsList = curItem;
            },
            // 设置标签
            setTags(route, oldroute){
                console.log(this.tagsList);
                const isExist = this.tagsList.some(item => {
                    return item.path === route.path;
                })
                const curItem = this.tagsList.filter(item => {
                    return item.path === this.$route.path;
                })
                if (curItem.length == 1) {
                    curItem[0].fullPath = route.fullPath;
                }
                if (route.query.closeFlag) {
                    if(route.query.works === 1) {
                        !isExist && this.tagsList.push({
                            title: route.meta.title,
                            path: route.path,
                            fullPath: route.fullPath
                        })
                    } else {
                        let $t = this
                        this.tagsList.forEach((item, index) => {
                            if (item.path === oldroute.path) {
                                $t.closeTags(index)
                                let fullpath = route.fullPath.slice(0, route.fullPath.indexOf('?'))
                                !isExist && this.tagsList.push({
                                    title: route.meta.title,
                                    path: route.path,
                                    fullPath: fullpath
                                })
                            }
                        })
                    }
                } else {
                    !isExist && this.tagsList.push({
                        title: route.meta.title,
                        path: route.path,
                        fullPath: route.fullPath
                    })
                }
            },
            handleTags(command){
                command === 'other' ? this.closeOther() : this.closeAll();
            }
        },
        computed: {
            showTags() {
                return this.tagsList.length > 0;
            }
        },
        watch:{
            '$route' (newValue, oldValue){
                this.setTags(newValue, oldValue);
            }
        },
        created(){
            this.setTags(this.$route);
        }
    }

</script>


<style>
    .tags {
        position: relative;
        height: 30px;
        overflow: hidden;
        background: #fff;
        padding-right: 120px;
    }

    .tags ul {
        box-sizing: border-box;
        width: 100%;
        height: 100%;
    }

    .tags-li {
        float: left;
        margin: 3px 5px 2px 3px;
        border-radius: 3px;
        font-size: 12px;
        overflow: hidden;
        cursor: pointer;
        height: 23px;
        line-height: 23px;
        border: 1px solid #e9eaec;
        background: #fff;
        padding: 0 5px 0 12px;
        vertical-align: middle;
        color: #666;
        -webkit-transition: all .3s ease-in;
        -moz-transition: all .3s ease-in;
        transition: all .3s ease-in;
    }

    .tags-li:not(.active):hover {
        background: #f8f8f8;
    }

    .tags-li.active {
        color: #fff;
    }

    .tags-li-title {
        float: left;
        max-width: 80px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        margin-right: 5px;
        color: #666;
    }

    .tags-li.active .tags-li-title {
        color: #fff;
    }

    .tags-close-box {
        position: absolute;
        right: 0;
        top: 0;
        box-sizing: border-box;
        padding-top: 1px;
        text-align: center;
        width: 110px;
        height: 30px;
        background: #fff;
        box-shadow: -3px 0 15px 3px rgba(0, 0, 0, .1);
        z-index: 10;
    }

</style>
