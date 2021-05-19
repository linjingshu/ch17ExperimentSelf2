/**
* 删除左右两端的空格   
* 注：在JavaScript的ECMAScript 5版本中，已支持trim函数。对应的浏览器版本最低要求：IE 9.0、Chrome 10.0、FireFox 3.5、Safari 5.0、Opera 10.5
* 若浏览器不支持 trim() 方法，则可用正则表达式来实现
*/
String.prototype.trim=function(){
     return this.replace(/(^\s*)|(\s*$)/g, '');
}
