<template>
    <div class="dagre-graph-container">
        <Dagre-Graph :nodes="nodes" :edges="edges" types="materials"></Dagre-Graph>
    </div>
</template>
<script>
    /**
     * 流程图
     */
    import DagreGraph from './dagreD3'
    export default {
        name: 'FlowDiagram',
        data() {
            return {
                nodes: [],
                edges: []
            }
        },
        created() {
            this.init()
        },
        methods: {
            init () {
                this.nodes = [
                    {
                        id: 'e1',
                        state: '',
                        value: {
                            label: '来料不合格评审发起',
                            operator: 'Sponsor'
                        }
                    },
                    {
                        id: 'e2',
                        state: 'queued',
                        value: {
                            label: '纠正及处置措施',
                            operator: 'BranchPythonMapOperator'
                        }
                    },
                    {
                        id: 'e3',
                        state: 'queued',
                        value: {
                            label: '不合格原因分析',
                            operator: 'BranchPythonMapOperator'
                        }
                    },
                    {
                        id: 'e2-1',
                        state: '',
                        value: {
                            label: '让步使用',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e2-2',
                        state: '',
                        value: {
                            label: '物料转换',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e2-3',
                        state: '',
                        value: {
                            label: '换货',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e2-4',
                        state: '',
                        value: {
                            label: '内部损耗',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e2-5',
                        state: '',
                        value: {
                            label: '退货',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e2-1-1',
                        state: 'success',
                        value: {
                            label: '评审结果',
                            operator: 'BashOperator',
                            bgc: 'red'
                        }
                    },
                    {
                        id: 'e2-2-1',
                        state: 'success',
                        value: {
                            label: '评审结果',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e2-3-1',
                        state: 'success',
                        value: {
                            label: '评审结果',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e2-4-1',
                        state: 'success',
                        value: {
                            label: '评审结果',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e2-5-1',
                        state: 'success',
                        value: {
                            label: '评审结果',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e2-1-1-1',
                        state: 'failed',
                        value: {
                            label: '不同意',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e2-2-1-1',
                        state: 'failed',
                        value: {
                            label: '不同意',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e2-3-1-1',
                        state: 'failed',
                        value: {
                            label: '不同意',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e2-4-1-1',
                        state: 'failed',
                        value: {
                            label: '不同意',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e2-5-1-1',
                        state: 'failed',
                        value: {
                            label: '不同意',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e2-x-x-x-result',
                        state: 'success',
                        value: {
                            label: '评审结果',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e2-x-x-x-s-checkout',
                        state: '',
                        value: {
                            label: '跟踪检验',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e2-4-1-statistics',
                        state: '',
                        value: {
                            label: '损失统计',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'finish',
                        state: 'upstream_failed',
                        value: {
                            label: '结束',
                            operator: 'JoinOperator'
                        }
                    }
                ]
                this.edges = [
                    {
                        from: 'e1',
                        to: 'e2',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e1',
                        to: 'e3',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2',
                        to: 'e2-1',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2',
                        to: 'e2-2',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2',
                        to: 'e2-3',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2',
                        to: 'e2-4',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2',
                        to: 'e2-5',
                        color: '#3d85c6'
                    },
                    //让步使用-评审结果
                    {
                        from: 'e2-1',
                        to: 'e2-1-1',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2-2',
                        to: 'e2-2-1',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2-3',
                        to: 'e2-3-1',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2-4',
                        to: 'e2-4-1',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2-5',
                        to: 'e2-5-1',
                        color: '#3d85c6'
                    },
                    // 评审结果-不同意
                    {
                        from: 'e2-1-1',
                        to: 'e2-1-1-1',
                        color: '#ff0000'
                    },
                    {
                        from: 'e2-2-1',
                        to: 'e2-2-1-1',
                        color: '#ff0000'
                    },
                    {
                        from: 'e2-3-1',
                        to: 'e2-3-1-1',
                        color: '#ff0000'
                    },
                    {
                        from: 'e2-4-1',
                        to: 'e2-4-1-1',
                        color: '#ff0000'
                    },
                    {
                        from: 'e2-5-1',
                        to: 'e2-5-1-1',
                        color: '#ff0000'
                    },
                    //不同意-评审结果
                    {
                        from: 'e2-1-1-1',
                        to: 'e2-x-x-x-result',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2-2-1-1',
                        to: 'e2-x-x-x-result',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2-3-1-1',
                        to: 'e2-x-x-x-result',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2-4-1-1',
                        to: 'e2-x-x-x-result',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2-5-1-1',
                        to: 'e2-x-x-x-result',
                        color: '#3d85c6'
                    },
                    //跟踪检验
                    {
                        from: 'e2-1-1',
                        to: 'e2-x-x-x-s-checkout',
                        color: '#6aa84f'
                    },
                    {
                        from: 'e2-2-1',
                        to: 'e2-x-x-x-s-checkout',
                        color: '#6aa84f'
                    },
                    {
                        from: 'e2-3-1',
                        to: 'e2-x-x-x-s-checkout',
                        color: '#6aa84f'
                    },
                    {
                        from: 'e2-x-x-x-result',
                        to: 'e2-x-x-x-s-checkout',
                        color: '#6aa84f'
                    },
                    //评审结果-损失统计
                    {
                        from: 'e2-4-1-1',
                        to: 'e2-4-1-statistics',
                        color: '#00ffff'
                    },
                    {
                        from: 'e2-4-1-statistics',
                        to: 'finish',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2-x-x-x-s-checkout',
                        to: 'finish',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e3',
                        to: 'finish',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2-5-1',
                        to: 'finish',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2-x-x-x-result',
                        to: 'e2',
                        color: '#ff0000'
                    }
                ]
            }
        },
        components: {
            DagreGraph
        }
    }
</script>
<style lang='scss' scoped>
    .dagre-graph-container {
        width: 100%;
        height: 800px;
        margin-top: 20px;
    }
</style>