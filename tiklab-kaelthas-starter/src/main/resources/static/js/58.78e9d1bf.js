(window.webpackJsonp=window.webpackJsonp||[]).push([[58],{1180:function(e,t,n){"use strict";n.r(t);var r=n(0),o=n.n(r),a=n(300),i=n(63),c="D:\\code\\companycode\\xmonitor\\kaelthas-ui\\tiklab-kaelthas-ui\\src\\host\\setting\\common\\SettingLeftTabs.js";function u(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=n){var r,o,a,i,c=[],u=!0,l=!1;try{if(a=(n=n.call(e)).next,0===t){if(Object(n)!==n)return;u=!1}else for(;!(u=(r=a.call(n)).done)&&(c.push(r.value),c.length!==t);u=!0);}catch(e){l=!0,o=e}finally{try{if(!u&&null!=n.return&&(i=n.return(),Object(i)!==i))return}finally{if(l)throw o}}return c}}(e,t)||function(e,t){if(!e)return;if("string"==typeof e)return l(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);"Object"===n&&e.constructor&&(n=e.constructor.name);if("Map"===n||"Set"===n)return Array.from(e);if("Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n))return l(e,t)}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function l(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}var m=Object(i.h)((function(e){var t=localStorage.getItem("hostId"),n=[{name:"主机信息",url:"/host/".concat(t,"/projectInformation"),key:"setting",encoded:"setting"},{name:"成员",url:"/host/".concat(t,"/member"),key:"member",encoded:"member"},{name:"权限",url:"/host/".concat(t,"/permissions"),key:"permissions",encoded:"permissions"}],a=u(Object(r.useState)("/host/".concat(t,"/projectInformation")),2),i=a[0],l=a[1];return Object(r.useEffect)((function(){l(e.location.pathname)}),[t]),o.a.createElement("div",{className:"setting-box-right-left",__source:{fileName:c,lineNumber:43,columnNumber:9}},o.a.createElement("div",{className:"setting-box-right-text title",__source:{fileName:c,lineNumber:44,columnNumber:13}},"设置"),o.a.createElement("div",{className:"setting-box-menu",__source:{fileName:c,lineNumber:47,columnNumber:13}},n.map((function(t,n){return o.a.createElement("div",{key:n,onClick:function(){return n=t.url,l(n),void e.history.push(n);var n},className:"setting-box-right-tabs ".concat(t.url===i?"setting-setting-select":""),__source:{fileName:c,lineNumber:51,columnNumber:29}},o.a.createElement("span",{__source:{fileName:c,lineNumber:56,columnNumber:33}},t.name))}))))})),s="D:\\code\\companycode\\xmonitor\\kaelthas-ui\\tiklab-kaelthas-ui\\src\\host\\setting\\common\\SettingLayout.js";t.default=Object(i.h)((function(e){var t=e.route;return o.a.createElement("div",{className:"setting-layout",__source:{fileName:s,lineNumber:14,columnNumber:9}},o.a.createElement(m,{__source:{fileName:s,lineNumber:15,columnNumber:17}}),o.a.createElement("div",{className:"setting-layout-right",__source:{fileName:s,lineNumber:16,columnNumber:13}},Object(a.a)(t.routes)))}))}}]);