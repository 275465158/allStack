<template>
    <div class="dagre-graph-container">
        <Dagre-Graph :nodes="nodes" :edges="edges" types="manufacture"></Dagre-Graph>
    </div>
</template>
<script>
    /**
     * 流程图
     */
    import DagreGraph from './dagreD3'
    export default {
        name: 'FlowDiagram11',
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
                        state: '',
                        value: {
                            label: '责任评定',
                            operator: 'Sponsor'
                        }
                    },
                    {
                        id: 'e3-1',
                        state: 'queued',
                        value: {
                            label: '不合格原因分析',
                            operator: 'BranchPythonMapOperator'
                        }
                    },
                    {
                        id: 'e3-2',
                        state: 'queued',
                        value: {
                            label: '纠正及处置措施',
                            operator: 'BranchPythonMapOperator'
                        }
                    },
                    {
                        id: 'e4-1',
                        state: '',
                        value: {
                            label: '让步使用',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e4-2',
                        state: '',
                        value: {
                            label: '物料转换',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e4-3',
                        state: '',
                        value: {
                            label: '报废',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e4-4',
                        state: '',
                        value: {
                            label: '返工/返修',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e5-1',
                        state: 'success',
                        value: {
                            label: '评审结果',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e5-2',
                        state: 'success',
                        value: {
                            label: '评审结果',
                            operator: 'BashOperator',
                            bgc: 'red'
                        }
                    },
                    {
                        id: 'e5-3',
                        state: 'success',
                        value: {
                            label: '评审结果',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e5-4',
                        state: 'success',
                        value: {
                            label: '评审结果',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e6-1',
                        state: 'failed',
                        value: {
                            label: '不同意',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e6-2',
                        state: 'failed',
                        value: {
                            label: '不同意',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e6-3',
                        state: 'failed',
                        value: {
                            label: '不同意',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e6-4',
                        state: 'failed',
                        value: {
                            label: '不同意',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e7',
                        state: 'success',
                        value: {
                            label: '评审结果',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e8-1',
                        state: '',
                        value: {
                            label: '跟踪检验',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e8-2',
                        state: '',
                        value: {
                            label: '损失统计',
                            operator: 'BashOperator'
                        }
                    },
                    {
                        id: 'e9',
                        state: 'success',
                        value: {
                            label: '处置结果',
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
                        from: 'e2',
                        to: 'e3-1',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e2',
                        to: 'e3-2',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e3-2',
                        to: 'e4-1',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e3-2',
                        to: 'e4-2',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e3-2',
                        to: 'e4-3',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e3-2',
                        to: 'e4-4',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e4-1',
                        to: 'e5-1',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e4-2',
                        to: 'e5-2',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e4-3',
                        to: 'e5-3',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e4-4',
                        to: 'e5-4',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e5-1',
                        to: 'e6-1',
                        color: '#ff0000'
                    },
                    {
                        from: 'e5-2',
                        to: 'e6-2',
                        color: '#ff0000'
                    },
                    {
                        from: 'e5-3',
                        to: 'e6-3',
                        color: '#ff0000'
                    },
                    {
                        from: 'e5-4',
                        to: 'e6-4',
                        color: '#ff0000'
                    },
                    {
                        from: 'e6-1',
                        to: 'e7',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e6-2',
                        to: 'e7',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e6-3',
                        to: 'e7',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e6-4',
                        to: 'e7',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e5-1',
                        to: 'e8-1',
                        color: '#6aa84f'
                    },
                    {
                        from: 'e5-2',
                        to: 'e8-1',
                        color: '#6aa84f'
                    },
                    {
                        from: 'e5-4',
                        to: 'e8-1',
                        color: '#6aa84f'
                    },
                    {
                        from: 'e7',
                        to: 'e8-1',
                        color: '#6aa84f'
                    },
                    {
                        from: 'e7',
                        to: 'e3-2',
                        color: '#ff0000'
                    },
                    {
                        from: 'e5-3',
                        to: 'e8-2',
                        color: '#00ffff'
                    },
                    {
                        from: 'e5-4',
                        to: 'e8-2',
                        color: '#00ffff'
                    },
                    {
                        from: 'e8-1',
                        to: 'e9',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e9',
                        to: 'e3-1',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e9',
                        to: 'finish',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e3-1',
                        to: 'finish',
                        color: '#3d85c6'
                    },
                    {
                        from: 'e8-2',
                        to: 'finish',
                        color: '#3d85c6'
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