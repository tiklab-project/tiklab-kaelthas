(window.webpackJsonp=window.webpackJsonp||[]).push([[13],{1164:function(e,t,r){"use strict";r(558);var n,a,i,o,c,l=r(557),u=(r(95),r(67)),s=r(0),f=r.n(s),p=r(6),m=r(927),h=r(937),d=(r(52),r(27)),y=(r(71),r(33)),v=(r(39),r(26)),b=(r(200),r(179)),g=(r(75),r(29)),w=(r(73),r(20)),E=r(340),k=r(282),O=r(10);function j(e){return(j="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function _(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */_=function(){return t};var e,t={},r=Object.prototype,n=r.hasOwnProperty,a=Object.defineProperty||function(e,t,r){e[t]=r.value},i="function"==typeof Symbol?Symbol:{},o=i.iterator||"@@iterator",c=i.asyncIterator||"@@asyncIterator",l=i.toStringTag||"@@toStringTag";function u(e,t,r){return Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}),e[t]}try{u({},"")}catch(e){u=function(e,t,r){return e[t]=r}}function s(e,t,r,n){var i=t&&t.prototype instanceof y?t:y,o=Object.create(i.prototype),c=new P(n||[]);return a(o,"_invoke",{value:x(e,r,c)}),o}function f(e,t,r){try{return{type:"normal",arg:e.call(t,r)}}catch(e){return{type:"throw",arg:e}}}t.wrap=s;var p="suspendedStart",m="executing",h="completed",d={};function y(){}function v(){}function b(){}var g={};u(g,o,(function(){return this}));var w=Object.getPrototypeOf,E=w&&w(w(T([])));E&&E!==r&&n.call(E,o)&&(g=E);var k=b.prototype=y.prototype=Object.create(g);function O(e){["next","throw","return"].forEach((function(t){u(e,t,(function(e){return this._invoke(t,e)}))}))}function L(e,t){function r(a,i,o,c){var l=f(e[a],e,i);if("throw"!==l.type){var u=l.arg,s=u.value;return s&&"object"==j(s)&&n.call(s,"__await")?t.resolve(s.__await).then((function(e){r("next",e,o,c)}),(function(e){r("throw",e,o,c)})):t.resolve(s).then((function(e){u.value=e,o(u)}),(function(e){return r("throw",e,o,c)}))}c(l.arg)}var i;a(this,"_invoke",{value:function(e,n){function a(){return new t((function(t,a){r(e,n,t,a)}))}return i=i?i.then(a,a):a()}})}function x(t,r,n){var a=p;return function(i,o){if(a===m)throw new Error("Generator is already running");if(a===h){if("throw"===i)throw o;return{value:e,done:!0}}for(n.method=i,n.arg=o;;){var c=n.delegate;if(c){var l=S(c,n);if(l){if(l===d)continue;return l}}if("next"===n.method)n.sent=n._sent=n.arg;else if("throw"===n.method){if(a===p)throw a=h,n.arg;n.dispatchException(n.arg)}else"return"===n.method&&n.abrupt("return",n.arg);a=m;var u=f(t,r,n);if("normal"===u.type){if(a=n.done?h:"suspendedYield",u.arg===d)continue;return{value:u.arg,done:n.done}}"throw"===u.type&&(a=h,n.method="throw",n.arg=u.arg)}}}function S(t,r){var n=r.method,a=t.iterator[n];if(a===e)return r.delegate=null,"throw"===n&&t.iterator.return&&(r.method="return",r.arg=e,S(t,r),"throw"===r.method)||"return"!==n&&(r.method="throw",r.arg=new TypeError("The iterator does not provide a '"+n+"' method")),d;var i=f(a,t.iterator,r.arg);if("throw"===i.type)return r.method="throw",r.arg=i.arg,r.delegate=null,d;var o=i.arg;return o?o.done?(r[t.resultName]=o.value,r.next=t.nextLoc,"return"!==r.method&&(r.method="next",r.arg=e),r.delegate=null,d):o:(r.method="throw",r.arg=new TypeError("iterator result is not an object"),r.delegate=null,d)}function N(e){var t={tryLoc:e[0]};1 in e&&(t.catchLoc=e[1]),2 in e&&(t.finallyLoc=e[2],t.afterLoc=e[3]),this.tryEntries.push(t)}function A(e){var t=e.completion||{};t.type="normal",delete t.arg,e.completion=t}function P(e){this.tryEntries=[{tryLoc:"root"}],e.forEach(N,this),this.reset(!0)}function T(t){if(t||""===t){var r=t[o];if(r)return r.call(t);if("function"==typeof t.next)return t;if(!isNaN(t.length)){var a=-1,i=function r(){for(;++a<t.length;)if(n.call(t,a))return r.value=t[a],r.done=!1,r;return r.value=e,r.done=!0,r};return i.next=i}}throw new TypeError(j(t)+" is not iterable")}return v.prototype=b,a(k,"constructor",{value:b,configurable:!0}),a(b,"constructor",{value:v,configurable:!0}),v.displayName=u(b,l,"GeneratorFunction"),t.isGeneratorFunction=function(e){var t="function"==typeof e&&e.constructor;return!!t&&(t===v||"GeneratorFunction"===(t.displayName||t.name))},t.mark=function(e){return Object.setPrototypeOf?Object.setPrototypeOf(e,b):(e.__proto__=b,u(e,l,"GeneratorFunction")),e.prototype=Object.create(k),e},t.awrap=function(e){return{__await:e}},O(L.prototype),u(L.prototype,c,(function(){return this})),t.AsyncIterator=L,t.async=function(e,r,n,a,i){void 0===i&&(i=Promise);var o=new L(s(e,r,n,a),i);return t.isGeneratorFunction(r)?o:o.next().then((function(e){return e.done?e.value:o.next()}))},O(k),u(k,l,"Generator"),u(k,o,(function(){return this})),u(k,"toString",(function(){return"[object Generator]"})),t.keys=function(e){var t=Object(e),r=[];for(var n in t)r.push(n);return r.reverse(),function e(){for(;r.length;){var n=r.pop();if(n in t)return e.value=n,e.done=!1,e}return e.done=!0,e}},t.values=T,P.prototype={constructor:P,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=e,this.done=!1,this.delegate=null,this.method="next",this.arg=e,this.tryEntries.forEach(A),!t)for(var r in this)"t"===r.charAt(0)&&n.call(this,r)&&!isNaN(+r.slice(1))&&(this[r]=e)},stop:function(){this.done=!0;var e=this.tryEntries[0].completion;if("throw"===e.type)throw e.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var r=this;function a(n,a){return c.type="throw",c.arg=t,r.next=n,a&&(r.method="next",r.arg=e),!!a}for(var i=this.tryEntries.length-1;i>=0;--i){var o=this.tryEntries[i],c=o.completion;if("root"===o.tryLoc)return a("end");if(o.tryLoc<=this.prev){var l=n.call(o,"catchLoc"),u=n.call(o,"finallyLoc");if(l&&u){if(this.prev<o.catchLoc)return a(o.catchLoc,!0);if(this.prev<o.finallyLoc)return a(o.finallyLoc)}else if(l){if(this.prev<o.catchLoc)return a(o.catchLoc,!0)}else{if(!u)throw new Error("try statement without catch or finally");if(this.prev<o.finallyLoc)return a(o.finallyLoc)}}}},abrupt:function(e,t){for(var r=this.tryEntries.length-1;r>=0;--r){var a=this.tryEntries[r];if(a.tryLoc<=this.prev&&n.call(a,"finallyLoc")&&this.prev<a.finallyLoc){var i=a;break}}i&&("break"===e||"continue"===e)&&i.tryLoc<=t&&t<=i.finallyLoc&&(i=null);var o=i?i.completion:{};return o.type=e,o.arg=t,i?(this.method="next",this.next=i.finallyLoc,d):this.complete(o)},complete:function(e,t){if("throw"===e.type)throw e.arg;return"break"===e.type||"continue"===e.type?this.next=e.arg:"return"===e.type?(this.rval=this.arg=e.arg,this.method="return",this.next="end"):"normal"===e.type&&t&&(this.next=t),d},finish:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.finallyLoc===e)return this.complete(r.completion,r.afterLoc),A(r),d}},catch:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.tryLoc===e){var n=r.completion;if("throw"===n.type){var a=n.arg;A(r)}return a}}throw new Error("illegal catch attempt")},delegateYield:function(t,r,n){return this.delegate={iterator:T(t),resultName:r,nextLoc:n},"next"===this.method&&(this.arg=e),d}},t}function L(e,t,r,n,a,i,o){try{var c=e[i](o),l=c.value}catch(e){return void r(e)}c.done?t(l):Promise.resolve(l).then(n,a)}function x(e){return function(){var t=this,r=arguments;return new Promise((function(n,a){var i=e.apply(t,r);function o(e){L(i,n,a,o,c,"next",e)}function c(e){L(i,n,a,o,c,"throw",e)}o(void 0)}))}}function S(e,t,r,n){r&&Object.defineProperty(e,t,{enumerable:r.enumerable,configurable:r.configurable,writable:r.writable,value:r.initializer?r.initializer.call(n):void 0})}function N(e,t){for(var r=0;r<t.length;r++){var n=t[r];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,P(n.key),n)}}function A(e,t,r){return t&&N(e.prototype,t),r&&N(e,r),Object.defineProperty(e,"prototype",{writable:!1}),e}function P(e){var t=function(e,t){if("object"!==j(e)||null===e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!==j(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"===j(t)?t:String(t)}function T(e,t,r,n,a){var i={};return Object.keys(n).forEach((function(e){i[e]=n[e]})),i.enumerable=!!i.enumerable,i.configurable=!!i.configurable,("value"in i||i.initializer)&&(i.writable=!0),i=r.slice().reverse().reduce((function(r,n){return n(e,t,r)||r}),i),a&&void 0!==i.initializer&&(i.value=i.initializer?i.initializer.call(a):void 0,i.initializer=void 0),void 0===i.initializer&&(Object.defineProperty(e,t,i),i=null),i}var C=new(a=T((n=A((function e(){!function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,e),S(this,"findAppLinkList",a,this),S(this,"createAppLink",i,this),S(this,"updateAppLink",o,this),S(this,"deleteAppLink",c,this)}))).prototype,"findAppLinkList",[O.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return x(_().mark((function e(){var t;return _().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,p.Axios.post("/appLink/findAppLinkList",{});case 2:if((t=e.sent).code){e.next=5;break}return e.abrupt("return",t.data);case 5:return e.abrupt("return",[]);case 6:case"end":return e.stop()}}),e)})))}}),i=T(n.prototype,"createAppLink",[O.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var e=x(_().mark((function e(t){return _().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,p.Axios.post("/appLink/createAppLink",t);case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()}}),o=T(n.prototype,"updateAppLink",[O.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var e=x(_().mark((function e(t){return _().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,p.Axios.post("/appLink/updateAppLink",t);case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()}}),c=T(n.prototype,"deleteAppLink",[O.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var e=x(_().mark((function e(t){var r;return _().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return(r=new FormData).append("id",t),e.next=4,p.Axios.post("/appLink/deleteAppLink",r);case 4:return e.abrupt("return",e.sent);case 5:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()}}),n),I=r(498),F=r(187);function U(e){return(U="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function G(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function z(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?G(Object(r),!0).forEach((function(t){D(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):G(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function D(e,t,r){return(t=function(e){var t=function(e,t){if("object"!==U(e)||null===e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!==U(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"===U(t)?t:String(t)}(t))in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function V(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,a,i,o,c=[],l=!0,u=!1;try{if(i=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;l=!1}else for(;!(l=(n=i.call(r)).done)&&(c.push(n.value),c.length!==t);l=!0);}catch(e){u=!0,a=e}finally{try{if(!l&&null!=r.return&&(o=r.return(),Object(o)!==o))return}finally{if(u)throw a}}return c}}(e,t)||function(e,t){if(!e)return;if("string"==typeof e)return q(e,t);var r=Object.prototype.toString.call(e).slice(8,-1);"Object"===r&&e.constructor&&(r=e.constructor.name);if("Map"===r||"Set"===r)return Array.from(e);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return q(e,t)}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function q(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=new Array(t);r<t;r++)n[r]=e[r];return n}var M=function(e){var t=e.visible,r=e.applications,n=e.setVisible,a=e.setApps,i=e.edit,o=e.setEdit,c=C.createAppLink,l=C.updateAppLink,h=C.deleteAppLink,O=V(w.default.useForm(),1)[0];Object(s.useEffect)((function(){t&&j()}),[t]);var j=function(){r.length>0?o(z(z({},r[0]),{},{edit:"edit"})):o({edit:"add"})};Object(s.useEffect)((function(){i&&O.setFieldsValue({appUrl:i.appUrl,appType:i.appType})}),[i]);var _=function(){O.resetFields(),o(null),n(!1)};return f.a.createElement(I.a,{visible:t,width:800,onCancel:_,footer:null},f.a.createElement("div",{className:"tiklab_manage_app"},f.a.createElement("div",{className:"tiklab_manage_app_bottom"},f.a.createElement("div",{className:"app_bottom_product"},f.a.createElement("div",{className:"tiklab_manage_app_up"},"应用导航配置"),f.a.createElement("div",{className:"product_auto"},r.map((function(e){return f.a.createElement("div",{key:e.id,className:"app_bottom_item ".concat((null==i?void 0:i.id)===e.id?"app_bottom_item_active":""),onClick:function(){return function(e){o(z({edit:"edit"},e))}(e)}},f.a.createElement("div",{className:"app_bottom_item_img"},f.a.createElement("img",{width:24,height:24,src:p.productImg[e.appType],alt:e.label})),f.a.createElement("div",{className:"app_bottom_item_content"},p.productTitle[e.appType]),f.a.createElement("div",{className:"app_bottom_item_btn"},f.a.createElement(b.default,{placement:"topRight",title:"你确定删除吗",onConfirm:function(t){return function(e,t){e.stopPropagation(),h(t.id).then((function(e){0===e.code?(t.id===i.id?a(z({},i),"clear"):a(z({},i),"delete"),g.default.success("删除成功!")):g.default.error("删除失败!")}))}(t,e)},okText:"确定",cancelText:"取消"},f.a.createElement(E.default,{onClick:function(e){return e.stopPropagation()}}))))})),0===r.length&&f.a.createElement(u.default,{description:f.a.createElement("span",{className:"app-link-empty"},"无应用导航，点击添加配置")})),f.a.createElement(v.default,{className:"app_bottom_item product-add",onClick:function(){O.resetFields(),o({edit:"add"})}},f.a.createElement(k.default,{style:{fontSize:16}})," 添加配置")),f.a.createElement("div",{className:"app_bottom_form"},f.a.createElement("div",{className:"tiklab_manage_form_up"},f.a.createElement("div",{className:"app_bottom_title"},"edit"===(null==i?void 0:i.edit)?"修改":"添加"),f.a.createElement(F.a,{title:f.a.createElement(m.a,{style:{fontSize:16}}),type:"text",onClick:_})),f.a.createElement("div",{className:"product_auto"},f.a.createElement(w.default,{form:O,layout:"vertical"},f.a.createElement(w.default.Item,{label:"产品",name:"appType",rules:[{required:!0,message:"产品不能为空"}]},f.a.createElement(y.default,{disabled:"edit"===(null==i?void 0:i.edit)},p.product.map((function(e){return f.a.createElement(y.default.Option,{value:e,key:e,disabled:(t=e,r.some((function(e){return e.appType===t})))},p.productTitle[e]);var t})))),f.a.createElement(w.default.Item,{label:"应用导航地址",name:"appUrl",rules:[{required:!0,message:"地址不能为空"},{type:"url",message:"应用链接地址无效"}]},f.a.createElement(d.default,null)))),f.a.createElement("div",{className:"app_bottom_form_btn"},"edit"===(null==i?void 0:i.edit)?null:f.a.createElement(F.a,{onClick:j,title:"取消",isMar:!0}),f.a.createElement(F.a,{onClick:function(){O.validateFields().then((function(e){if("edit"===(null==i?void 0:i.edit)){if(e.appUrl===i.appUrl)return;l(z(z({},e),{},{id:i.id})).then((function(t){0===t.code?(a(z(z({},e),{},{id:i.id}),"edit"),g.default.success("修改成功!")):g.default.error("修改失败!")}))}else c(e).then((function(t){0===t.code?(a(z(z({},e),{},{id:t.data}),"add"),g.default.success("添加成功!")):g.default.error("添加失败!")}))}))},title:"保存",type:"primary"}))))))},Y=r(565);function $(e){return($="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function R(){return(R=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var n in r)Object.prototype.hasOwnProperty.call(r,n)&&(e[n]=r[n])}return e}).apply(this,arguments)}function J(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function X(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?J(Object(r),!0).forEach((function(t){W(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):J(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function W(e,t,r){return(t=function(e){var t=function(e,t){if("object"!==$(e)||null===e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!==$(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"===$(t)?t:String(t)}(t))in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function B(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */B=function(){return t};var e,t={},r=Object.prototype,n=r.hasOwnProperty,a=Object.defineProperty||function(e,t,r){e[t]=r.value},i="function"==typeof Symbol?Symbol:{},o=i.iterator||"@@iterator",c=i.asyncIterator||"@@asyncIterator",l=i.toStringTag||"@@toStringTag";function u(e,t,r){return Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}),e[t]}try{u({},"")}catch(e){u=function(e,t,r){return e[t]=r}}function s(e,t,r,n){var i=t&&t.prototype instanceof y?t:y,o=Object.create(i.prototype),c=new N(n||[]);return a(o,"_invoke",{value:_(e,r,c)}),o}function f(e,t,r){try{return{type:"normal",arg:e.call(t,r)}}catch(e){return{type:"throw",arg:e}}}t.wrap=s;var p="suspendedStart",m="executing",h="completed",d={};function y(){}function v(){}function b(){}var g={};u(g,o,(function(){return this}));var w=Object.getPrototypeOf,E=w&&w(w(A([])));E&&E!==r&&n.call(E,o)&&(g=E);var k=b.prototype=y.prototype=Object.create(g);function O(e){["next","throw","return"].forEach((function(t){u(e,t,(function(e){return this._invoke(t,e)}))}))}function j(e,t){function r(a,i,o,c){var l=f(e[a],e,i);if("throw"!==l.type){var u=l.arg,s=u.value;return s&&"object"==$(s)&&n.call(s,"__await")?t.resolve(s.__await).then((function(e){r("next",e,o,c)}),(function(e){r("throw",e,o,c)})):t.resolve(s).then((function(e){u.value=e,o(u)}),(function(e){return r("throw",e,o,c)}))}c(l.arg)}var i;a(this,"_invoke",{value:function(e,n){function a(){return new t((function(t,a){r(e,n,t,a)}))}return i=i?i.then(a,a):a()}})}function _(t,r,n){var a=p;return function(i,o){if(a===m)throw new Error("Generator is already running");if(a===h){if("throw"===i)throw o;return{value:e,done:!0}}for(n.method=i,n.arg=o;;){var c=n.delegate;if(c){var l=L(c,n);if(l){if(l===d)continue;return l}}if("next"===n.method)n.sent=n._sent=n.arg;else if("throw"===n.method){if(a===p)throw a=h,n.arg;n.dispatchException(n.arg)}else"return"===n.method&&n.abrupt("return",n.arg);a=m;var u=f(t,r,n);if("normal"===u.type){if(a=n.done?h:"suspendedYield",u.arg===d)continue;return{value:u.arg,done:n.done}}"throw"===u.type&&(a=h,n.method="throw",n.arg=u.arg)}}}function L(t,r){var n=r.method,a=t.iterator[n];if(a===e)return r.delegate=null,"throw"===n&&t.iterator.return&&(r.method="return",r.arg=e,L(t,r),"throw"===r.method)||"return"!==n&&(r.method="throw",r.arg=new TypeError("The iterator does not provide a '"+n+"' method")),d;var i=f(a,t.iterator,r.arg);if("throw"===i.type)return r.method="throw",r.arg=i.arg,r.delegate=null,d;var o=i.arg;return o?o.done?(r[t.resultName]=o.value,r.next=t.nextLoc,"return"!==r.method&&(r.method="next",r.arg=e),r.delegate=null,d):o:(r.method="throw",r.arg=new TypeError("iterator result is not an object"),r.delegate=null,d)}function x(e){var t={tryLoc:e[0]};1 in e&&(t.catchLoc=e[1]),2 in e&&(t.finallyLoc=e[2],t.afterLoc=e[3]),this.tryEntries.push(t)}function S(e){var t=e.completion||{};t.type="normal",delete t.arg,e.completion=t}function N(e){this.tryEntries=[{tryLoc:"root"}],e.forEach(x,this),this.reset(!0)}function A(t){if(t||""===t){var r=t[o];if(r)return r.call(t);if("function"==typeof t.next)return t;if(!isNaN(t.length)){var a=-1,i=function r(){for(;++a<t.length;)if(n.call(t,a))return r.value=t[a],r.done=!1,r;return r.value=e,r.done=!0,r};return i.next=i}}throw new TypeError($(t)+" is not iterable")}return v.prototype=b,a(k,"constructor",{value:b,configurable:!0}),a(b,"constructor",{value:v,configurable:!0}),v.displayName=u(b,l,"GeneratorFunction"),t.isGeneratorFunction=function(e){var t="function"==typeof e&&e.constructor;return!!t&&(t===v||"GeneratorFunction"===(t.displayName||t.name))},t.mark=function(e){return Object.setPrototypeOf?Object.setPrototypeOf(e,b):(e.__proto__=b,u(e,l,"GeneratorFunction")),e.prototype=Object.create(k),e},t.awrap=function(e){return{__await:e}},O(j.prototype),u(j.prototype,c,(function(){return this})),t.AsyncIterator=j,t.async=function(e,r,n,a,i){void 0===i&&(i=Promise);var o=new j(s(e,r,n,a),i);return t.isGeneratorFunction(r)?o:o.next().then((function(e){return e.done?e.value:o.next()}))},O(k),u(k,l,"Generator"),u(k,o,(function(){return this})),u(k,"toString",(function(){return"[object Generator]"})),t.keys=function(e){var t=Object(e),r=[];for(var n in t)r.push(n);return r.reverse(),function e(){for(;r.length;){var n=r.pop();if(n in t)return e.value=n,e.done=!1,e}return e.done=!0,e}},t.values=A,N.prototype={constructor:N,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=e,this.done=!1,this.delegate=null,this.method="next",this.arg=e,this.tryEntries.forEach(S),!t)for(var r in this)"t"===r.charAt(0)&&n.call(this,r)&&!isNaN(+r.slice(1))&&(this[r]=e)},stop:function(){this.done=!0;var e=this.tryEntries[0].completion;if("throw"===e.type)throw e.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var r=this;function a(n,a){return c.type="throw",c.arg=t,r.next=n,a&&(r.method="next",r.arg=e),!!a}for(var i=this.tryEntries.length-1;i>=0;--i){var o=this.tryEntries[i],c=o.completion;if("root"===o.tryLoc)return a("end");if(o.tryLoc<=this.prev){var l=n.call(o,"catchLoc"),u=n.call(o,"finallyLoc");if(l&&u){if(this.prev<o.catchLoc)return a(o.catchLoc,!0);if(this.prev<o.finallyLoc)return a(o.finallyLoc)}else if(l){if(this.prev<o.catchLoc)return a(o.catchLoc,!0)}else{if(!u)throw new Error("try statement without catch or finally");if(this.prev<o.finallyLoc)return a(o.finallyLoc)}}}},abrupt:function(e,t){for(var r=this.tryEntries.length-1;r>=0;--r){var a=this.tryEntries[r];if(a.tryLoc<=this.prev&&n.call(a,"finallyLoc")&&this.prev<a.finallyLoc){var i=a;break}}i&&("break"===e||"continue"===e)&&i.tryLoc<=t&&t<=i.finallyLoc&&(i=null);var o=i?i.completion:{};return o.type=e,o.arg=t,i?(this.method="next",this.next=i.finallyLoc,d):this.complete(o)},complete:function(e,t){if("throw"===e.type)throw e.arg;return"break"===e.type||"continue"===e.type?this.next=e.arg:"return"===e.type?(this.rval=this.arg=e.arg,this.method="return",this.next="end"):"normal"===e.type&&t&&(this.next=t),d},finish:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.finallyLoc===e)return this.complete(r.completion,r.afterLoc),S(r),d}},catch:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.tryLoc===e){var n=r.completion;if("throw"===n.type){var a=n.arg;S(r)}return a}}throw new Error("illegal catch attempt")},delegateYield:function(t,r,n){return this.delegate={iterator:A(t),resultName:r,nextLoc:n},"next"===this.method&&(this.arg=e),d}},t}function H(e,t,r,n,a,i,o){try{var c=e[i](o),l=c.value}catch(e){return void r(e)}c.done?t(l):Promise.resolve(l).then(n,a)}function K(e){return function(){var t=this,r=arguments;return new Promise((function(n,a){var i=e.apply(t,r);function o(e){H(i,n,a,o,c,"next",e)}function c(e){H(i,n,a,o,c,"throw",e)}o(void 0)}))}}function Q(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,a,i,o,c=[],l=!0,u=!1;try{if(i=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;l=!1}else for(;!(l=(n=i.call(r)).done)&&(c.push(n.value),c.length!==t);l=!0);}catch(e){u=!0,a=e}finally{try{if(!l&&null!=r.return&&(o=r.return(),Object(o)!==o))return}finally{if(u)throw a}}return c}}(e,t)||function(e,t){if(!e)return;if("string"==typeof e)return Z(e,t);var r=Object.prototype.toString.call(e).slice(8,-1);"Object"===r&&e.constructor&&(r=e.constructor.name);if("Map"===r||"Set"===r)return Array.from(e);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return Z(e,t)}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function Z(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=new Array(t);r<t;r++)n[r]=e[r];return n}var ee=function(e){var t=e.bgroup,r=e.homeStore,n=e.iconComponent,a=e.style,i=e.translateX,o=void 0===i?75:i,c=C.findAppLinkList,d=Q(Object(s.useState)(!1),2),y=d[0],v=d[1],b=Q(Object(s.useState)([]),2),g=b[0],w=b[1],E=Q(Object(s.useState)(!1),2),k=E[0],O=E[1],j=Q(Object(s.useState)(null),2),_=j[0],L=j[1];Object(s.useEffect)(K(B().mark((function e(){return B().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,x();case 2:case"end":return e.stop()}}),e)}))),[]);var x=function(){var e=K(B().mark((function e(){var t;return B().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,c();case 2:(t=e.sent).length>0&&N(t);case 4:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}(),S=function(){var e=K(B().mark((function e(t,r){var n;return B().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,c();case 2:(n=e.sent).length>0?(N(n),L(X(X({},"clear"===r?n[0]:t),{},{edit:"edit"}))):(N([]),L({edit:"add"}));case 4:case"end":return e.stop()}}),e)})));return function(t,r){return e.apply(this,arguments)}}(),N=function(e){"soular"===t?null==r||r.setInstallApp(e):w(e)},A="soular"===t?null==r?void 0:r.installApp:g;return f.a.createElement(Y.a,{visible:y,setVisible:v,type:"applink",tooltip:"应用导航",Icon:n},y&&f.a.createElement(l.default,R({placement:"left",onClose:function(){return v(!1)},visible:y,width:280,mask:!0,closable:!1,bodyStyle:{padding:0},maskStyle:{background:"transparent"},contentWrapperStyle:y?{transform:"translateX(".concat(o,"px)")}:{}},a),f.a.createElement("div",{className:"tiklab_apps_link"},f.a.createElement("div",{className:"tiklab_apps_icon_menu_up"},f.a.createElement("div",null,"应用导航"),f.a.createElement(F.a,{title:f.a.createElement(m.a,{style:{fontSize:16}}),type:"text",onClick:function(){return v(!1)}})),f.a.createElement("div",{className:"tiklab_apps_icon_menu_main"},A.map((function(e,t){return f.a.createElement("div",{className:"tiklab_apps_item",key:e.id+"-"+t,onClick:function(){return function(e){var t=Object(p.getUser)(),r=t.ticket?"".concat(e.appUrl,"?").concat(Object(p.parseUserSearchParams)({ticket:t.ticket})):e.appUrl;v(!1),window.open(r)}(e)}},f.a.createElement("img",{src:p.productImg[e.appType],alt:e.appType,width:24,height:24}),f.a.createElement("div",{className:"tiklab_apps_item_main"},p.productTitle[e.appType]))})),0===A.length&&f.a.createElement(u.default,{description:f.a.createElement("span",{className:"app-link-empty"},"无应用数据，点击编辑配置")})),f.a.createElement("div",{onClick:function(){O(!0),v(!1)},className:"tiklab_apps_link_config"},f.a.createElement("span",null,f.a.createElement(h.a,null)),f.a.createElement("span",null,"配置")))),f.a.createElement(M,{edit:_,setEdit:L,visible:k,setVisible:O,applications:A,setApps:S}))};t.a=ee},1181:function(e,t,r){"use strict";var n=r(0),a=r.n(n),i=r(938),o=r(939),c=r(940),l=r(941),u=r(565),s=r.p+"images/vip-light.png",f=r.p+"images/vip-dark.png",p=r(6);function m(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,a,i,o,c=[],l=!0,u=!1;try{if(i=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;l=!1}else for(;!(l=(n=i.call(r)).done)&&(c.push(n.value),c.length!==t);l=!0);}catch(e){u=!0,a=e}finally{try{if(!l&&null!=r.return&&(o=r.return(),Object(o)!==o))return}finally{if(u)throw a}}return c}}(e,t)||function(e,t){if(!e)return;if("string"==typeof e)return h(e,t);var r=Object.prototype.toString.call(e).slice(8,-1);"Object"===r&&e.constructor&&(r=e.constructor.name);if("Map"===r||"Set"===r)return Array.from(e);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return h(e,t)}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function h(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=new Array(t);r<t;r++)n[r]=e[r];return n}t.a=function(e){var t=e.iconComponent,r=e.bgroup,h=m(Object(n.useState)(!1),2),d=h[0],y=h[1],v=Object(p.disableFunction)(),b=function(e){return y(!1),"document"===e?window.open("https://doc.tiklab.cn/"):"question"===e?window.open("https://community.tiklab.cn/question"):void window.open("https://tiklab.cn/".concat(e))};return a.a.createElement(u.a,{width:240,tooltip:"帮助与支持",Icon:t,visible:d,setVisible:y},a.a.createElement("div",{className:"licence-help-link",onClick:function(){return b("document")}},a.a.createElement(i.a,{className:"licence-help-link-icon"}),"文档"),a.a.createElement("div",{className:"licence-help-link",onClick:function(){return b("question")}},a.a.createElement(o.a,{className:"licence-help-link-icon"}),"社区支持"),a.a.createElement("div",{className:"licence-help-link",onClick:function(){return b("account/workOrder")}},a.a.createElement(c.a,{className:"licence-help-link-icon"}),"在线工单"),a.a.createElement("div",{className:"licence-help-link",onClick:function(){return b("account/group")}},a.a.createElement(l.a,{className:"licence-help-link-icon"}),"在线客服"),a.a.createElement("div",{className:"licence-help-link-feature"},a.a.createElement("div",null,a.a.createElement("span",{className:"licence-help-link-icon"},a.a.createElement("img",{src:v?f:s,alt:"vip",width:18,height:18})),v?"社区版":"企业版"),v?a.a.createElement("div",{className:"licence-help-link-feature-sub",onClick:function(){y(!1),Object(p.applySubscription)(r)}},"订阅"):null))}},1218:function(e,t,r){"use strict";r(90);var n=r(74),a=(r(39),r(26)),i=r(0),o=r.n(i),c=r(6),l=r(942),u=r(320),s=r(943),f=r(944),p=r(166),m=r(565),h=r(496);function d(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,a,i,o,c=[],l=!0,u=!1;try{if(i=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;l=!1}else for(;!(l=(n=i.call(r)).done)&&(c.push(n.value),c.length!==t);l=!0);}catch(e){u=!0,a=e}finally{try{if(!l&&null!=r.return&&(o=r.return(),Object(o)!==o))return}finally{if(u)throw a}}return c}}(e,t)||function(e,t){if(!e)return;if("string"==typeof e)return y(e,t);var r=Object.prototype.toString.call(e).slice(8,-1);"Object"===r&&e.constructor&&(r=e.constructor.name);if("Map"===r||"Set"===r)return Array.from(e);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return y(e,t)}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function y(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=new Array(t);r<t;r++)n[r]=e[r];return n}t.a=function(e){var t=e.iconComponent,r=e.changeTheme,y=Object(p.a)().i18n,v=d(Object(i.useState)(y.languages),1)[0],b=d(Object(i.useState)(!1),2),g=b[0],w=b[1];return o.a.createElement(m.a,{width:300,tooltip:"个人中心",Icon:t,visible:g,setVisible:w,bottom:20},o.a.createElement("div",{className:"licence-avatar-link-head"},o.a.createElement(a.default,null,o.a.createElement(h.a,null),o.a.createElement("div",{className:"licence-avatar-link-user"},o.a.createElement("div",null,Object(c.getUser)().nickname||Object(c.getUser)().name||"用户"),o.a.createElement("div",null,Object(c.getUser)().phone||Object(c.getUser)().eamil||"--")))),o.a.createElement(n.default,{overlay:o.a.createElement("div",{className:"licence-theme-option"},o.a.createElement("div",{className:"theme-option-item",onClick:function(){return r("default")}},o.a.createElement("div",{className:"theme-option-default"}),o.a.createElement("div",null,"默认")),o.a.createElement("div",{className:"theme-option-item",onClick:function(){return r("black")}},o.a.createElement("div",{className:"theme-option-black"}),o.a.createElement("div",null,"黑色")),o.a.createElement("div",{className:"theme-option-item",onClick:function(){return r("blue")}},o.a.createElement("div",{className:"theme-option-blue"}),o.a.createElement("div",null,"蓝色")))},o.a.createElement("div",{className:"licence-avatar-link-lan"},o.a.createElement(a.default,null,o.a.createElement(l.a,{className:"licence-avatar-link-icon"}),"切换主题"),o.a.createElement(u.default,null))),o.a.createElement(n.default,{overlay:o.a.createElement("div",{className:"licence-link-lan"},v.map((function(e){return o.a.createElement("div",{className:"link-lan-item",key:e},e)})))},o.a.createElement("div",{className:"licence-avatar-link-lan"},o.a.createElement(a.default,null,o.a.createElement(s.a,{className:"licence-avatar-link-icon"}),"切换语言"),o.a.createElement(u.default,null))),o.a.createElement("div",{className:"licence-avatar-link-out",onClick:function(){w(!1),e.history.push({pathname:"/logout",state:{preRoute:e.location.pathname}})}},o.a.createElement(a.default,null,o.a.createElement(f.a,{className:"licence-avatar-link-icon"}),"退出")))}},565:function(e,t,r){"use strict";var n=r(0),a=r.n(n);t.a=function(e){var t=e.type,r=e.visible,i=e.setVisible,o=(e.tooltip,e.children),c=e.Icon,l=e.width,u=void 0===l?140:l,s=e.bottom,f=Object(n.useRef)(null);Object(n.useEffect)((function(){return window.addEventListener("click",p,!1),function(){window.removeEventListener("click",p,!1)}}),[r]);var p=function(e){f&&"applink"!==t&&(f.current&&f.current.contains(e.target)||i(!1))};return a.a.createElement("div",{className:"licence_dropdown",ref:f},a.a.createElement("div",{onClick:function(){return i(!r)},className:"".concat(r?"licence_dropdown_active":"")},c),o&&a.a.createElement("div",{style:{width:u,bottom:s},className:"licence_dropdown_menu ".concat(r?"":"licence_dropdown_hidden")},o))}}}]);