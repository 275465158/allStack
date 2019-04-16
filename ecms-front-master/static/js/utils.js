export function getFileName(filePath) {
    let s = filePath.substring(filePath.lastIndexOf('/') + 1);
    return s.substring(s.indexOf("_") + 1);
}