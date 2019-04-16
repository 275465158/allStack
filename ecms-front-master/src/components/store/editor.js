export const SEL_NODETYPE = 'SEL_NODETYPE'
export const SEL_LINETYPE = 'SEL_LINETYPE'
export const UPDATE_NODE = 'UPDATE_NODE'
export const UPDATE_HOVER_NODE = 'UPDATE_HOVER_NODE'
export const UPDATE_LINE = 'UPDATE_LINE'
export const UPDATE_SHADOW_LINE = 'UPDATE_SHADOW_LINE'
export const UPDATE_DRAWSTYLE = 'UPDATE_DRAWSTYLE'
export const UNDO = 'UNDO'
export const REDO = 'REDO'
export const UPDATE_HISTORY = 'UPDATE_HISTORY'
export const UPDATE_HISTORYINDEX = 'UPDATE_HISTORYINDEX'
export const UPDATE_STENCILS = "UPDATE_STENCILS"
export const UPDATE_SELECTED_NODE = 'UPDATE_SELECTED_NODE'
export const UPDATE_MODEL = "UPDATE_MODEL"
export const UPDATE_NODE_OUTGOING = "UPDATE_NODE_OUTGOING"
export const DELETE_NODE_OUTGOING = "DELETE_NODE_OUTGOING"
export const DELETE_LINE = "DELETE_LINE"
export const DELETE_NODE = "DELETE_NODE"
export const CLEAR_MODEL = "CLEAR_MODEL"
export const CLEAR_NODE = "CLEAR_NODE"
export const CLEAR_LINE = "CLEAR_LINE"


const store = {
    namespaced: true,
    state: {
        //节点模型
        stencilSet: [],
        //设计模型根节点元素
        modelData: {},
        // 添加节点类型
        selNodeType: '',
        //添加节点视图
        selNodeView: "",
        // 节点数据
        nodeData: {},
        //正在移动的节点数据
        hoverNodeData: {
            id: ''
        },
        // 连线数据
        lineData: null,
        shadowLineData: null,
        // 画布样式
        drawStyle: {
            zoomRate: 1 // 全局缩放比例
        },
        selLineType: 'StraightLine',
        historyLength: 0,
        historyIndex: 0,
        selectedNode: {}
    },
    mutations: {
        // 选中节点类型
        [SEL_NODETYPE](state, value) {
            state.selNodeType = value.id;
            state.selNodeView = value.view;
        },
        // 选中线条类型
        [SEL_LINETYPE](state, value = '') {
            state.selLineType = value
        },
        // 修改画布大小位置等
        [UPDATE_DRAWSTYLE](state, value) {
            state.drawStyle = {
                ...state.drawStyle,
                ...value
            }
        },
        [UPDATE_NODE](state, value) {
            state.nodeData = {
                ...state.nodeData,
                ...value
            }
            //console.log(state.nodeData);
        },
        [UPDATE_NODE_OUTGOING](state, value) {
            for (let nodeIndex in state.nodeData) {
                for (let lineIndex in value) {
                    if (nodeIndex == lineIndex) {
                        state.nodeData[nodeIndex].outgoing.push(value[lineIndex].outgoing);
                    }
                }
            }
        },
        [DELETE_NODE_OUTGOING](state, value) {
            for (let nodeIdx in value) {
                for (let lineIdx in state.nodeData[nodeIdx].outgoing) {
                    if (value[nodeIdx].outgoing.resourceId == state.nodeData[nodeIdx].outgoing[lineIdx].resourceId) {
                        //delete state.nodeData[nodeIdx].outgoing[lineIdx];
                        state.nodeData[nodeIdx].outgoing.splice(lineIdx, 1);
                    }
                }
            }
            state.nodeData = Object.assign({}, state.nodeData);
        },
        [UPDATE_HOVER_NODE](state, value) {
            state.hoverNodeData = {
                ...state.hoverNodeData,
                ...value
            }
        },
        [UPDATE_SELECTED_NODE](state, value) {
            if (value != '') {
                state.selectedNode =
                    {
                        ...state.selectedNode,
                        ...value
                    };
            } else {
                state.selectedNode = Object.assign({}, "");
            }

        },
        [UPDATE_LINE](state, value) {
            state.lineData = {
                ...state.lineData,
                ...value
            };
        },
        [DELETE_LINE](state, value) {
            let delLineIdx = null;
            for (let lineIdx in state.lineData) {
                if (state.lineData[lineIdx].resourceId == value.resourceId) {
                    delLineIdx = lineIdx;
                }
            }
            if (delLineIdx != null) {
                delete state.lineData[delLineIdx];
            }
            state.lineData = Object.assign({}, state.lineData);
        },
        [DELETE_NODE](state, value) {
            let delNodeIdx = null;
            for (let nodeIdx in state.nodeData) {
                if (state.nodeData[nodeIdx].resourceId == value.id) {
                    delNodeIdx = nodeIdx;
                }
            }
            if (delNodeIdx != null) {
                delete state.nodeData[delNodeIdx];
            }
            state.nodeData = Object.assign({}, state.nodeData);
        },
        [UPDATE_SHADOW_LINE](state, value) {
            state.shadowLineData = value
        },
        [UPDATE_HISTORY](state, value) {
            state.historyLength = value
        },
        [UPDATE_HISTORYINDEX](state, value) {
            state.historyIndex = value
        },
        [CLEAR_MODEL](state, value) {
            state.modelData = {};
        },
        [CLEAR_NODE](state, value) {
            state.nodeData = {};
        },
        [CLEAR_LINE](state, value) {
            state.lineData = {};
        },
        [UNDO](state, value = 0) { },
        [REDO](state, value = 0) { },
        [UPDATE_MODEL](state, value) {
            state.modelData = {
                ...state.modelData,
                ...value
            };
        },
        [UPDATE_STENCILS](state, value) {
            state.stencilSet = {
                ...state.stencilSet,
                ...value
            }
        }
    },
    actions: {

    }
}

export default store