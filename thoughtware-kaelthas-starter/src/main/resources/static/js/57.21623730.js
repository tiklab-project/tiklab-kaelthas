(window.webpackJsonp=window.webpackJsonp||[]).push([[57],{1168:function(e,t,r){"use strict";r.r(t);var n=r(0),o=r.n(n),a=r(300),i=r(63),c="D:\\code\\companycode\\xmonitor\\thoughtware-kaelthas-ui\\src\\host\\setting\\common\\SettingLeftTabs.js";function u(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,o,a,i,c=[],u=!0,l=!1;try{if(a=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;u=!1}else for(;!(u=(n=a.call(r)).done)&&(c.push(n.value),c.length!==t);u=!0);}catch(e){l=!0,o=e}finally{try{if(!u&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(l)throw o}}return c}}(e,t)||function(e,t){if(!e)return;if("string"==typeof e)return l(e,t);var r=Object.prototype.toString.call(e).slice(8,-1);"Object"===r&&e.constructor&&(r=e.constructor.name);if("Map"===r||"Set"===r)return Array.from(e);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return l(e,t)}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function l(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=new Array(t);r<t;r++)n[r]=e[r];return n}var s=Object(i.h)((function(e){var t=localStorage.getItem("hostId"),r=[{name:"主机信息",url:"/hostList/".concat(t,"/projectInformation"),key:"setting",encoded:"setting"},{name:"成员",url:"/hostList/".concat(t,"/member"),key:"member",encoded:"member"},{name:"权限",url:"/hostList/".concat(t,"/permissions"),key:"permissions",encoded:"permissions"}],a=u(Object(n.useState)("/hostList/".concat(t,"/projectInformation")),2),i=a[0],l=a[1];return Object(n.useEffect)((function(){l(e.location.pathname)}),[t]),o.a.createElement("div",{className:"setting-box-right-left",__source:{fileName:c,lineNumber:43,columnNumber:9}},o.a.createElement("div",{className:"setting-box-right-text title",__source:{fileName:c,lineNumber:44,columnNumber:13}},"设置"),o.a.createElement("div",{className:"setting-box-menu",__source:{fileName:c,lineNumber:47,columnNumber:13}},r.map((function(t,r){return o.a.createElement("div",{key:r,onClick:function(){return r=t.url,l(r),void e.history.push(r);var r},className:"setting-box-right-tabs ".concat(t.url===i?"setting-setting-select":""),__source:{fileName:c,lineNumber:51,columnNumber:29}},o.a.createElement("span",{__source:{fileName:c,lineNumber:56,columnNumber:33}},t.name))}))))})),m="D:\\code\\companycode\\xmonitor\\thoughtware-kaelthas-ui\\src\\host\\setting\\common\\SettingLayout.js";t.default=Object(i.h)((function(e){var t=e.route;return o.a.createElement("div",{className:"setting-layout",__source:{fileName:m,lineNumber:14,columnNumber:9}},o.a.createElement(s,{__source:{fileName:m,lineNumber:15,columnNumber:17}}),o.a.createElement("div",{className:"setting-layout-right",__source:{fileName:m,lineNumber:16,columnNumber:13}},Object(a.a)(t.routes)))}))}}]);