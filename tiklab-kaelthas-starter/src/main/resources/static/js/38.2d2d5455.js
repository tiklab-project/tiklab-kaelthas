(window.webpackJsonp=window.webpackJsonp||[]).push([[38],{1071:function(e,t,r){"use strict";t.a=r.p+"images/vip-dark.png"},1072:function(e,t,r){"use strict";t.a=r.p+"images/vip-light.png"},1077:function(e,t,r){"use strict";var n,i,a,o=r(10),l=r(6);function c(e){return(c="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function u(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */u=function(){return t};var e,t={},r=Object.prototype,n=r.hasOwnProperty,i=Object.defineProperty||function(e,t,r){e[t]=r.value},a="function"==typeof Symbol?Symbol:{},o=a.iterator||"@@iterator",l=a.asyncIterator||"@@asyncIterator",s=a.toStringTag||"@@toStringTag";function f(e,t,r){return Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}),e[t]}try{f({},"")}catch(e){f=function(e,t,r){return e[t]=r}}function m(e,t,r,n){var a=t&&t.prototype instanceof b?t:b,o=Object.create(a.prototype),l=new A(n||[]);return i(o,"_invoke",{value:k(e,r,l)}),o}function p(e,t,r){try{return{type:"normal",arg:e.call(t,r)}}catch(e){return{type:"throw",arg:e}}}t.wrap=m;var d="suspendedStart",h="executing",v="completed",y={};function b(){}function g(){}function w(){}var E={};f(E,o,(function(){return this}));var x=Object.getPrototypeOf,N=x&&x(x(Y([])));N&&N!==r&&n.call(N,o)&&(E=N);var O=w.prototype=b.prototype=Object.create(E);function L(e){["next","throw","return"].forEach((function(t){f(e,t,(function(e){return this._invoke(t,e)}))}))}function j(e,t){function r(i,a,o,l){var u=p(e[i],e,a);if("throw"!==u.type){var s=u.arg,f=s.value;return f&&"object"==c(f)&&n.call(f,"__await")?t.resolve(f.__await).then((function(e){r("next",e,o,l)}),(function(e){r("throw",e,o,l)})):t.resolve(f).then((function(e){s.value=e,o(s)}),(function(e){return r("throw",e,o,l)}))}l(u.arg)}var a;i(this,"_invoke",{value:function(e,n){function i(){return new t((function(t,i){r(e,n,t,i)}))}return a=a?a.then(i,i):i()}})}function k(t,r,n){var i=d;return function(a,o){if(i===h)throw Error("Generator is already running");if(i===v){if("throw"===a)throw o;return{value:e,done:!0}}for(n.method=a,n.arg=o;;){var l=n.delegate;if(l){var c=S(l,n);if(c){if(c===y)continue;return c}}if("next"===n.method)n.sent=n._sent=n.arg;else if("throw"===n.method){if(i===d)throw i=v,n.arg;n.dispatchException(n.arg)}else"return"===n.method&&n.abrupt("return",n.arg);i=h;var u=p(t,r,n);if("normal"===u.type){if(i=n.done?v:"suspendedYield",u.arg===y)continue;return{value:u.arg,done:n.done}}"throw"===u.type&&(i=v,n.method="throw",n.arg=u.arg)}}}function S(t,r){var n=r.method,i=t.iterator[n];if(i===e)return r.delegate=null,"throw"===n&&t.iterator.return&&(r.method="return",r.arg=e,S(t,r),"throw"===r.method)||"return"!==n&&(r.method="throw",r.arg=new TypeError("The iterator does not provide a '"+n+"' method")),y;var a=p(i,t.iterator,r.arg);if("throw"===a.type)return r.method="throw",r.arg=a.arg,r.delegate=null,y;var o=a.arg;return o?o.done?(r[t.resultName]=o.value,r.next=t.nextLoc,"return"!==r.method&&(r.method="next",r.arg=e),r.delegate=null,y):o:(r.method="throw",r.arg=new TypeError("iterator result is not an object"),r.delegate=null,y)}function _(e){var t={tryLoc:e[0]};1 in e&&(t.catchLoc=e[1]),2 in e&&(t.finallyLoc=e[2],t.afterLoc=e[3]),this.tryEntries.push(t)}function T(e){var t=e.completion||{};t.type="normal",delete t.arg,e.completion=t}function A(e){this.tryEntries=[{tryLoc:"root"}],e.forEach(_,this),this.reset(!0)}function Y(t){if(t||""===t){var r=t[o];if(r)return r.call(t);if("function"==typeof t.next)return t;if(!isNaN(t.length)){var i=-1,a=function r(){for(;++i<t.length;)if(n.call(t,i))return r.value=t[i],r.done=!1,r;return r.value=e,r.done=!0,r};return a.next=a}}throw new TypeError(c(t)+" is not iterable")}return g.prototype=w,i(O,"constructor",{value:w,configurable:!0}),i(w,"constructor",{value:g,configurable:!0}),g.displayName=f(w,s,"GeneratorFunction"),t.isGeneratorFunction=function(e){var t="function"==typeof e&&e.constructor;return!!t&&(t===g||"GeneratorFunction"===(t.displayName||t.name))},t.mark=function(e){return Object.setPrototypeOf?Object.setPrototypeOf(e,w):(e.__proto__=w,f(e,s,"GeneratorFunction")),e.prototype=Object.create(O),e},t.awrap=function(e){return{__await:e}},L(j.prototype),f(j.prototype,l,(function(){return this})),t.AsyncIterator=j,t.async=function(e,r,n,i,a){void 0===a&&(a=Promise);var o=new j(m(e,r,n,i),a);return t.isGeneratorFunction(r)?o:o.next().then((function(e){return e.done?e.value:o.next()}))},L(O),f(O,s,"Generator"),f(O,o,(function(){return this})),f(O,"toString",(function(){return"[object Generator]"})),t.keys=function(e){var t=Object(e),r=[];for(var n in t)r.push(n);return r.reverse(),function e(){for(;r.length;){var n=r.pop();if(n in t)return e.value=n,e.done=!1,e}return e.done=!0,e}},t.values=Y,A.prototype={constructor:A,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=e,this.done=!1,this.delegate=null,this.method="next",this.arg=e,this.tryEntries.forEach(T),!t)for(var r in this)"t"===r.charAt(0)&&n.call(this,r)&&!isNaN(+r.slice(1))&&(this[r]=e)},stop:function(){this.done=!0;var e=this.tryEntries[0].completion;if("throw"===e.type)throw e.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var r=this;function i(n,i){return l.type="throw",l.arg=t,r.next=n,i&&(r.method="next",r.arg=e),!!i}for(var a=this.tryEntries.length-1;a>=0;--a){var o=this.tryEntries[a],l=o.completion;if("root"===o.tryLoc)return i("end");if(o.tryLoc<=this.prev){var c=n.call(o,"catchLoc"),u=n.call(o,"finallyLoc");if(c&&u){if(this.prev<o.catchLoc)return i(o.catchLoc,!0);if(this.prev<o.finallyLoc)return i(o.finallyLoc)}else if(c){if(this.prev<o.catchLoc)return i(o.catchLoc,!0)}else{if(!u)throw Error("try statement without catch or finally");if(this.prev<o.finallyLoc)return i(o.finallyLoc)}}}},abrupt:function(e,t){for(var r=this.tryEntries.length-1;r>=0;--r){var i=this.tryEntries[r];if(i.tryLoc<=this.prev&&n.call(i,"finallyLoc")&&this.prev<i.finallyLoc){var a=i;break}}a&&("break"===e||"continue"===e)&&a.tryLoc<=t&&t<=a.finallyLoc&&(a=null);var o=a?a.completion:{};return o.type=e,o.arg=t,a?(this.method="next",this.next=a.finallyLoc,y):this.complete(o)},complete:function(e,t){if("throw"===e.type)throw e.arg;return"break"===e.type||"continue"===e.type?this.next=e.arg:"return"===e.type?(this.rval=this.arg=e.arg,this.method="return",this.next="end"):"normal"===e.type&&t&&(this.next=t),y},finish:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.finallyLoc===e)return this.complete(r.completion,r.afterLoc),T(r),y}},catch:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.tryLoc===e){var n=r.completion;if("throw"===n.type){var i=n.arg;T(r)}return i}}throw Error("illegal catch attempt")},delegateYield:function(t,r,n){return this.delegate={iterator:Y(t),resultName:r,nextLoc:n},"next"===this.method&&(this.arg=e),y}},t}function s(e,t,r,n,i,a,o){try{var l=e[a](o),c=l.value}catch(e){return void r(e)}l.done?t(c):Promise.resolve(c).then(n,i)}function f(e){return function(){var t=this,r=arguments;return new Promise((function(n,i){var a=e.apply(t,r);function o(e){s(a,n,i,o,l,"next",e)}function l(e){s(a,n,i,o,l,"throw",e)}o(void 0)}))}}function m(e,t,r,n){r&&Object.defineProperty(e,t,{enumerable:r.enumerable,configurable:r.configurable,writable:r.writable,value:r.initializer?r.initializer.call(n):void 0})}function p(e,t){for(var r=0;r<t.length;r++){var n=t[r];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,h(n.key),n)}}function d(e,t,r){return t&&p(e.prototype,t),r&&p(e,r),Object.defineProperty(e,"prototype",{writable:!1}),e}function h(e){var t=function(e,t){if("object"!=c(e)||!e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!=c(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"==c(t)?t:t+""}function v(e,t,r,n,i){var a={};return Object.keys(n).forEach((function(e){a[e]=n[e]})),a.enumerable=!!a.enumerable,a.configurable=!!a.configurable,("value"in a||a.initializer)&&(a.writable=!0),a=r.slice().reverse().reduce((function(r,n){return n(e,t,r)||r}),a),i&&void 0!==a.initializer&&(a.value=a.initializer?a.initializer.call(i):void 0,a.initializer=void 0),void 0===a.initializer?(Object.defineProperty(e,t,a),null):a}var y=(i=v((n=d((function e(){var t,r,n;!function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,e),m(this,"getVersions",i,this),m(this,"findUseLicence",a,this),t=this,r="findAllLicence",n=f(u().mark((function e(){return u().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,l.Axios.post("/licence/findAllLicence");case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)}))),(r=h(r))in t?Object.defineProperty(t,r,{value:n,enumerable:!0,configurable:!0,writable:!0}):t[r]=n}))).prototype,"getVersions",[o.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return f(u().mark((function e(){return u().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,l.Axios.post("/version/getVersion");case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})))}}),a=v(n.prototype,"findUseLicence",[o.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return f(u().mark((function e(){return u().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,l.Axios.post("/licence/findUseLicence");case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})))}}),n);t.a=new y},1203:function(e,t,r){"use strict";r(133);var n=r(102),i=(r(66),r(43)),a=(r(65),r(45)),o=(r(55),r(36)),l=(r(564),r(292)),c=(r(156),r(91)),u=(r(73),r(28)),s=r(0),f=r.n(s),m=r(6),p=r(932),d=r(498),h=r(187),v=r(499),y=r(1071),b=r(1072),g=r(8),w=r.n(g),E=r(1077);function x(){return(x=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var n in r)({}).hasOwnProperty.call(r,n)&&(e[n]=r[n])}return e}).apply(null,arguments)}function N(e){return function(e){if(Array.isArray(e))return j(e)}(e)||function(e){if("undefined"!=typeof Symbol&&null!=e[Symbol.iterator]||null!=e["@@iterator"])return Array.from(e)}(e)||L(e)||function(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function O(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,i,a,o,l=[],c=!0,u=!1;try{if(a=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;c=!1}else for(;!(c=(n=a.call(r)).done)&&(l.push(n.value),l.length!==t);c=!0);}catch(e){u=!0,i=e}finally{try{if(!c&&null!=r.return&&(o=r.return(),Object(o)!==o))return}finally{if(u)throw i}}return l}}(e,t)||L(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function L(e,t){if(e){if("string"==typeof e)return j(e,t);var r={}.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?j(e,t):void 0}}function j(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=Array(t);r<t;r++)n[r]=e[r];return n}var k=function(e){var t=e.bgroup,r=e.children,g=E.a.getVersions,L=E.a.findUseLicence,j=E.a.findAllLicence,k=O(Object(s.useState)(null),2),S=k[0],_=k[1],T=O(Object(s.useState)(null),2),A=T[0],Y=T[1],P=O(Object(s.useState)([]),2),I=P[0],C=P[1],F=O(Object(s.useState)({}),2),z=F[0],M=F[1],G=O(Object(s.useState)(!1),2),D=G[0],H=G[1],U=O(Object(s.useState)(!1),2),V=U[0],J=U[1],K=O(Object(s.useState)(!0),2),R=K[0],$=K[1],q=window.location.origin;Object(s.useEffect)((function(){B()}),[]);var B=function(){g().then((function(e){0===e.code&&(M(e.data),localStorage.setItem(m.LOCALSTORAGE_KEY.VERSION_INFO,JSON.stringify(e.data)),e.data.expired||(Q(),W())),$(!1)}))},Q=function(){L().then((function(e){0===e.code&&e.data&&Y(e.data),6e3===e.code&&u.default.error(e.msg)}))},W=function(){j().then((function(e){0===e.code&&_(e.data)}))},X={name:"file",action:q+("/"===q.substring(q.length-1,q.length)?"licence/import":"/licence/import"),onChange:function(e){var t=N(e.fileList);if(t=(t=t.slice(-1)).map((function(e){return e.response&&(e.url=e.response.url),e})),C(t),e.file.status,"done"===e.file.status){var r=e.file;return 0===r.response.code?(setTimeout((function(){C([]),B()}),1e3),u.default.success("licence导入成功")):u.default.error("licence导入失败：".concat(r.response.msg))}if("error"===e.file.status)return u.default.error("licence导入失败，请重新上传")}},Z=[{title:"生效时间",dataIndex:"expiryTime",key:"expiryTime",width:"32%",ellipsis:!0,render:function(e){return w()(e).format("YYYY-MM-DD HH:mm:ss")}},{title:"过期时间",dataIndex:"issuedTime",key:"issuedTime",width:"30%",ellipsis:!0,render:function(e){return w()(e).format("YYYY-MM-DD HH:mm:ss")}},{title:"用户数",dataIndex:"userNum",key:"userNum",width:"32%",ellipsis:!0,render:function(e){return e>0?e:"无限制"}},{title:"状态",dataIndex:"state",key:"state",width:"16%",ellipsis:!0,render:function(e){return f.a.createElement(f.a.Fragment,null,1===e&&"已失效",2===e&&f.a.createElement("span",{className:"licence-history-modal-blue"},"生效中"),3===e&&"未生效")}}];return f.a.createElement(n.default,{spinning:R,tip:"导入licence中..."},f.a.createElement(i.default,{className:"tiklab_version"},f.a.createElement(a.default,{sm:{span:24},xs:{span:24},md:{span:24},lg:{span:"16",offset:"4"},xl:{span:"14",offset:"5"},xxl:{span:"14",offset:"5"}},f.a.createElement("div",{className:"tiklab_version_container"},f.a.createElement(d.a,{firstItem:"版本与许可证"},f.a.createElement("div",{className:"tiklab_version_up_btn"},2===(null==z?void 0:z.release)&&f.a.createElement(f.a.Fragment,null,f.a.createElement(l.a,x({},X,{fileList:I}),f.a.createElement(h.a,{isMar:!z.expired,type:"primary"},"导入licence")),!z.expired&&f.a.createElement(f.a.Fragment,null,f.a.createElement(h.a,{onClick:function(){return J(!0)}},"历史"),f.a.createElement(v.a,{title:f.a.createElement("div",{className:"licence-history-modal-title"},f.a.createElement("div",null,"历史"),f.a.createElement(h.a,{type:"text",onClick:function(){return J(!1)},title:f.a.createElement(p.a,null)})),width:800,visible:V,onCancel:function(){return J(!1)},footer:null},f.a.createElement("div",{className:"licence-history-modal"},f.a.createElement(o.default,{dataSource:S,columns:Z,pagination:!1,rowKey:function(e,t){return t},locale:{emptyText:f.a.createElement("div",{className:"licence-history-modal-empty"},"暂无历史")}}))))))),f.a.createElement("div",{className:"tiklab_version-info"},f.a.createElement("div",{className:"tiklab_version-info-item"},f.a.createElement("span",{className:"info-item-title"},"应用名称"),m.productTitle[t]),f.a.createElement("div",{className:"tiklab_version-info-item"},f.a.createElement("div",{className:"info-item-title"},"版本类型"),z.expired?f.a.createElement(f.a.Fragment,null,f.a.createElement("div",{className:"info-item-img"},f.a.createElement("img",{src:y.a,alt:"企业版特性",height:16,width:16})),f.a.createElement("div",{className:"info-item-info"},"社区版"),f.a.createElement("div",null,f.a.createElement("span",{className:"info-item-action",onClick:function(){return H(!0)}},"企业版特性"),f.a.createElement("span",{className:"info-item-action",onClick:function(){Object(m.applySubscription)(t)}},"订阅")),f.a.createElement(v.a,{width:800,visible:D,onCancel:function(){return H(!1)},footer:null,title:f.a.createElement("div",{className:"licence-version-feat-modal-title"},f.a.createElement("div",null,"企业版特性"),f.a.createElement(h.a,{type:"text",onClick:function(){return H(!1)},title:f.a.createElement(p.a,null)}))},f.a.createElement("div",{className:"licence-version-feat-modal"},r))):f.a.createElement(f.a.Fragment,null,f.a.createElement("div",{className:"info-item-img"},f.a.createElement("img",{src:b.a,alt:"企业版特性",height:16,width:16})),f.a.createElement("div",null,"企业版"),f.a.createElement("div",{className:"info-item-action"},z.expired?null!=A&&A.tryApply?f.a.createElement(c.default,{color:"#ff4d4f"},"试用已过期"):f.a.createElement(c.default,{color:"#ff4d4f"},"订阅已过期"):null!=A&&A.tryApply?f.a.createElement(c.default,{color:"#5d70ea"},"试用中"):f.a.createElement(c.default,{color:"#5d70ea"},"订阅中")))),f.a.createElement("div",{className:"tiklab_version-info-item"},f.a.createElement("span",{className:"info-item-title"},"用户数"),1===z.release||z.expired?"不限制":(null==A?void 0:A.userNum)>0?A.userNum+"人":"不限制"),f.a.createElement("div",{className:"tiklab_version-info-item"},f.a.createElement("span",{className:"info-item-title"},"生效时间"),null!=A&&A.expiryTime?w()(null==A?void 0:A.expiryTime).format("YYYY-MM-DD HH:mm:ss"):"--"),f.a.createElement("div",{className:"tiklab_version-info-item"},f.a.createElement("span",{className:"info-item-title"},"过期时间"),null!=A&&A.issuedTime?w()(null==A?void 0:A.issuedTime).format("YYYY-MM-DD HH:mm:ss"):"--"))))))};t.a=k}}]);