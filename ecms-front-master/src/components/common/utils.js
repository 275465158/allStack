
const deepCopy = function (s, t = {}) {
    for (var i in s) {
        if (typeof s[i] === 'object') {
            t[i] = s[i].constructor === Array ? [] : {};
            deepCopy(s[i], t[i]);
        } else {
            t[i] = s[i];
        }
    }
    return t;
}
const getElementOffset = function (element, targetId) {
    // 获取某元素相对于指定ID元素的偏移量，有translate的情况则不适合
    let {
        offsetLeft: left,
        offsetTop: top
    } = element;
    let current = element.offsetParent;

    while (current.offsetParent.id !== targetId) {
        left += current.offsetLeft;
        top += current.offsetTop;
        current = current.offsetParent;
    }
    return {
        top,
        left
    };
}

const pxToNumber = function (s) {
    return Number(s.replace('px', ''))
}

const getEleTopAndLeft = function (ele) {
    const {
        left,
        top
    } = ele.getBoundingClientRect()
    const {
        scrollLeft,
        scrollTop
    } = document.documentElement
    return {
        top: top + scrollTop,
        left: left + scrollLeft
    }
}

const getRelativePosition = function (son, parent) {
    const {
        left: sonLeft,
        top: sonTop
    } = getEleTopAndLeft(son)
    const {
        left: parentLeft,
        top: parentTop
    } = getEleTopAndLeft(parent)
    return {
        top: sonTop - parentTop,
        left: sonLeft - parentLeft
    }
}
const uuid = function (len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;

    if (len) {
        // Compact form
        for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
    } else {
        // rfc4122, version 4 form
        var r;

        // rfc4122 requires these characters
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';

        // Fill in random data.  At i==19 set the high bits of clock sequence as
        // per rfc4122, sec. 4.1.5
        for (i = 0; i < 36; i++) {
            if (!uuid[i]) {
                r = 0 | Math.random() * 16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
    }

    return uuid.join('');
}
//去除误差
const smoothPoint = function (point) {
    point.x = Math.round(point.x);
    point.y = Math.round(point.y);
    if (point.x % 2 == 1) {
        point.x = point.x + 1;
    }
    if (point.y % 2 == 1) {
        point.y = point.y + 1;
    }
    return point;
}
//计算两点之间的方向
const calculateDirection = function (source, target) {
    if (source.y == target.y) {
        return source.x <= target.x ? 1 : -1;
    } else {
        return source.y <= target.y ? 2 : -2;
    }
}
//获取下一个结束点
const offsetPoint = function (source, sourceD) {
    var FIX_LENGTH = 40;
    let gap = 0;
    let nextSourcePoint = { x: source.x, y: source.y };
    if (Math.abs(sourceD) == 1) {
        gap = sourceD > 0 ? FIX_LENGTH : 0 - FIX_LENGTH;
        nextSourcePoint.x += gap;
    } else if (Math.abs(sourceD) == 2) {
        gap = sourceD > 0 ? FIX_LENGTH : 0 - FIX_LENGTH;
        nextSourcePoint.y += gap;
    }
    return nextSourcePoint;
}
const calculateNodeDirection = function (source, target) {
    let dir = { x: 0, y: 0 };
    if (target.x != source.x) {
        dir.x = (target.x - source.x) / Math.abs(target.x - source.x);
    }
    if (target.y != source.y) {
        dir.y = (target.y - source.y) / Math.abs(target.y - source.y);
    }
    return dir;
}

const distance = function (a, b) {
    //三角函数取两点之间距离
    return Math.sqrt(
        (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y)
    );
}
const nodeOffsetPoint = function (nodeSource, source_width, source_height, nodeTarget, target_width, target_height) {
    let gap = 0;
    let sourceAnchorList = [
        //节点下方中点
        { x: (nodeSource.x + source_width / 2), y: (nodeSource.y + source_height + 20) },
        //节点上方中点
        { x: (nodeSource.x + source_width / 2), y: (nodeSource.y - 20) }
    ],
        targetAnchorList = [
            //节点下方中点
            { x: (nodeTarget.x + target_width / 2), y: (nodeTarget.y + target_height + 20) },
            //节点上方中点
            { x: (nodeTarget.x + target_width / 2), y: (nodeTarget.y - 20) }
        ];
    let disList = [];
    sourceAnchorList.forEach(sourceAnchor => {
        targetAnchorList.forEach(targetAnchor => {
            disList.push({ source: sourceAnchor, target: targetAnchor, distance: distance(sourceAnchor, targetAnchor) });
        });
    });
    //以排序方式取最短线段
    let minDistance = disList.sort(
        function (a, b) {
            return a.distance - b.distance;
        }
    )[0];
    return minDistance;
}
export {
    deepCopy,
    getElementOffset,
    pxToNumber,
    getEleTopAndLeft,
    getRelativePosition,
    uuid,
    smoothPoint,
    calculateDirection,
    offsetPoint,
    calculateNodeDirection,
    nodeOffsetPoint,
    distance
};