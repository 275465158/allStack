<template>
    <div id="editorLeftTool" class="editor-left-tool">
        <div class="flt-container">
            <div class="flt-search"></div>
            <ul>
                <li class="flt-tool-list" v-for="(tool, index ) in searchToolItems" :key="index">
                    <div class="flt-list-tit" @click="tool.visible=!tool.visible">
                        <icon
                            :name="tool.visible?'down-arrow':'up-arrow'"
                            :size="8"
                            class="flt-icon"
                        ></icon>
                        <span>{{tool.name}}</span>
                    </div>
                    <div class="flt-item-list" v-show="tool.visible">
                        <div class="flt-item-box">
                            <template>
                                <ul>
                                    <li
                                        class="flt-list-item"
                                        v-for="(item, index) in tool.items"
                                        :key="index"
                                    >
                                        <div
                                            draggable="true"
                                            @dragstart="selNode(item)"
                                            @dragend="nodeDragEnd"
                                        >{{item.name}}</div>
                                    </li>
                                </ul>
                            </template>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import axios from "axios";
export default {
    name: "editorLeftTool",
    data() {
        return {
            selItem: undefined,
            searchCont: "",
            morphRoles: [],
            stencilItemGroups: [],
            containmentRules: [],
            toolItems: [],
            userImages: []
        };
    },
    computed: {
        searchToolItems() {
            if (this.searchCont === "") {
                return this.stencilItemGroups;
            }
        }
    },
    methods: {
        ...mapMutations("editor", [
            "SEL_NODETYPE",
            "SET_DRAGGING",
            "SET_INDRAWAREA",
            "UPDATE_STENCILS"
        ]),
        selNode(type) {
            this.SEL_NODETYPE(type);
            event.dataTransfer.setData("Text", "add");
        },
        nodeDragEnd() {
            if (this.selNodeType) {
                this.SEL_NODETYPE("");
            }
        },
        dropHandle(e) {
            //let reader = new
        },
        dragoverHandle() {},
        dragstart(imgSrc) {
            event.dataTransfer.setData("URL", imgSrc);
            event.dataTransfer.setData("Text", "add");
        },
        init() {
            axios({
                method: "get",
                url:
                    "static/js/stencilset_bpmn.json?ver=" + new Date().getTime()
            }).then(res => {
                if (res != undefined) {
                    const stencils = res.data.stencils;
                    let quickMenuDefinition = [
                        "UserTask",
                        "EndNoneEvent",
                        "ExclusiveGateway",
                        "ParallelGateway",
                        "StartNoneEvent",
                        "TextAnnotion",
                        "SequenceFlow",
                        "Association"
                    ];
                    let ignoreForPaletteDefinition = [
                        "SequenceFlow",
                        "MessageFlow",
                        "Association",
                        "DataAssociation",
                        "DataStore",
                        "SendTask"
                    ];
                    let quickMenuItems = [];
                    let morphRoles = [];
                    for (
                        var i = 0;
                        i < res.data.rules.morphingRules.length;
                        i++
                    ) {
                        let role = res.data.rules.morphingRules[i].role;
                        let roleItem = { role: role, morphOptions: [] };
                        morphRoles.push(roleItem);
                    }

                    // Check all received items
                    for (
                        var stencilIndex = 0;
                        stencilIndex < res.data.stencils.length;
                        stencilIndex++
                    ) {
                        // Check if the root group is the 'diagram' group. If so, this item should not be shown.
                        let currentGroupName =
                            res.data.stencils[stencilIndex].groups[0];
                        if (
                            currentGroupName === "Diagram" ||
                            currentGroupName === "Form"
                        ) {
                            continue; // go to next item
                        }

                        let removed = false;
                        if (res.data.stencils[stencilIndex].removed) {
                            removed = true;
                        }

                        let currentGroup = undefined;
                        if (!removed) {
                            if (
                                currentGroupName !== null &&
                                currentGroupName !== undefined &&
                                currentGroupName.length > 0
                            ) {
                                currentGroup = this.findGroup(
                                    currentGroupName,
                                    this.stencilItemGroups
                                ); // Find group in root groups array
                                if (currentGroup === null) {
                                    currentGroup = this.addGroup(
                                        currentGroupName,
                                        this.stencilItemGroups
                                    );
                                }

                                // Add all child groups (if any)
                                for (
                                    var groupIndex = 1;
                                    groupIndex <
                                    res.data.stencils[stencilIndex].groups
                                        .length;
                                    groupIndex++
                                ) {
                                    let childGroupName =
                                        res.data.stencils[stencilIndex].groups[
                                            groupIndex
                                        ];
                                    let childGroup = this.findGroup(
                                        childGroupName,
                                        currentGroup.groups
                                    );
                                    if (childGroup === null) {
                                        childGroup = this.addGroup(
                                            childGroupName,
                                            currentGroup.groups
                                        );
                                    }

                                    // The current group variable holds the parent of the next group (if any),
                                    // and is basically the last element in the array of groups defined in the stencil item
                                    currentGroup = childGroup;
                                }
                            }
                        }

                        // Construct the stencil item
                        let stencilItem = {
                            id: res.data.stencils[stencilIndex].id,
                            name: res.data.stencils[stencilIndex].title,
                            description:
                                res.data.stencils[stencilIndex].description,
                            icon: res.data.stencils[stencilIndex].icon,
                            type: res.data.stencils[stencilIndex].type,
                            roles: res.data.stencils[stencilIndex].roles,
                            view: res.data.stencils[stencilIndex].view,
                            removed: removed,
                            customIcon: false,
                            canConnect: false,
                            canConnectTo: false,
                            canConnectAssociation: false,
                            assignee: "",
                            assigneeGroup: "",
                            width:res.data.stencils[stencilIndex].width,
                            height:res.data.stencils[stencilIndex].height
                        };

                        if (
                            res.data.stencils[stencilIndex].customIconId &&
                            res.data.stencils[stencilIndex].customIconId > 0
                        ) {
                            stencilItem.customIcon = true;
                            stencilItem.icon =
                                res.data.stencils[stencilIndex].customIconId;
                        }
                        if (!removed) {
                            if (
                                quickMenuDefinition.indexOf(stencilItem.id) >= 0
                            ) {
                                quickMenuItems[
                                    quickMenuDefinition.indexOf(stencilItem.id)
                                ] = stencilItem;
                            }
                        }
                        if (
                            stencilItem.id === "TextAnnotation" ||
                            stencilItem.id === "BoundaryCompensationEvent"
                        ) {
                            stencilItem.canConnectAssociation = true;
                        }

                        for (
                            var i = 0;
                            i < res.data.stencils[stencilIndex].roles.length;
                            i++
                        ) {
                            let stencilRole =
                                res.data.stencils[stencilIndex].roles[i];
                            if (stencilRole === "sequence_start") {
                                stencilItem.canConnect = true;
                            } else if (stencilRole === "sequence_end") {
                                stencilItem.canConnectTo = true;
                            }

                            for (var j = 0; j < morphRoles.length; j++) {
                                if (stencilRole === morphRoles[j].role) {
                                    if (!removed) {
                                        morphRoles[j].morphOptions.push(
                                            stencilItem
                                        );
                                    }
                                    stencilItem.morphRole = morphRoles[j].role;
                                    break;
                                }
                            }
                        }
                        if (currentGroup) {
                            // Add the stencil item to the correct group
                            currentGroup.items.push(stencilItem);
                            if (
                                ignoreForPaletteDefinition.indexOf(
                                    stencilItem.id
                                ) < 0
                            ) {
                                currentGroup.paletteItems.push(stencilItem);
                            }
                        } else {
                            // It's a root stencil element
                            if (!removed) {
                                this.stencilItemGroups.push(stencilItem);
                            }
                        }
                    }

                    for (var i = 0; i < this.stencilItemGroups.length; i++) {
                        if (
                            this.stencilItemGroups[i].paletteItems &&
                            this.stencilItemGroups[i].paletteItems.length == 0
                        ) {
                            this.stencilItemGroups[i].visible = false;
                        }
                    }

                    //this.stencilItemGroups = stencilItemGroups;

                    let containmentRules = [];
                    for (
                        var i = 0;
                        i < res.data.rules.containmentRules.length;
                        i++
                    ) {
                        var rule = res.data.rules.containmentRules[i];
                        containmentRules.push(rule);
                    }
                    this.containmentRules = containmentRules;

                    // remove quick menu items which are not available anymore due to custom pallette
                    let availableQuickMenuItems = [];
                    for (var i = 0; i < quickMenuItems.length; i++) {
                        if (quickMenuItems[i]) {
                            availableQuickMenuItems[
                                availableQuickMenuItems.length
                            ] = quickMenuItems[i];
                            this.UPDATE_STENCILS({
                                [quickMenuItems[i].id]: quickMenuItems[i]
                            });
                        }
                    }

                    this.toolItems = availableQuickMenuItems;
                    this.morphRoles = morphRoles;
                }
            });
        },
        findGroup(name, groupArray) {
            for (var index = 0; index < groupArray.length; index++) {
                if (groupArray[index].name === name) {
                    return groupArray[index];
                }
            }
            return null;
        },
        addGroup(groupName, groupArray) {
            let group = {
                name: groupName,
                items: [],
                paletteItems: [],
                groups: [],
                visible: true
            };
            groupArray.push(group);
            return group;
        }
    },
    mounted() {
        this.init();
    }
};
</script>

<style lang="scss">
.editor-left-tool {
    width: 208px;
    position: absolute;
    top: 66px;
    bottom: 0;
    background: whitesmoke;
    overflow: auto;
    box-shadow: -1px 0px 5px #bbb inset;
    border-right: 1px solid #ddd;
    .flt-container {
        .flt-list-tit {
            color: #333;
            background: #eee;
            padding: 6px 0px 6px 14px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            line-height: 1.4em;
            font-size: 9pt;
            display: flex;
            align-items: center;
            cursor: pointer;
        }
        ul {
            margin-right: 3px;
        }
        .flt-icon {
            margin-right: 8px;
        }
        .flt-item-list {
            // display: flex;
            flex-wrap: wrap;
            background: #f5f5f5;
            .flt-item-box {
                // display: flex;
                padding: 1px;
                flex-wrap: wrap;
                align-items: center;
                cursor: move;
                .flt-list-item {
                    line-height: 30px;
                    overflow: hidden;
                    cursor: move;
                    background: none;
                    margin-left: 10px;
                }
            }
        }
    }
}
</style>
