(window.webpackJsonp=window.webpackJsonp||[]).push([[58],{1177:function(e,t,n){"use strict";n.r(t);var r=n(0),u=n.n(r),a=n(300),o=n(63),i="D:\\code\\companycode\\xmonitor\\thoughtware-kaelthas-ui\\src\\k8s\\setting\\common\\KuSettingTabs.js";function c(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=n){var r,u,a,o,i=[],c=!0,l=!1;try{if(a=(n=n.call(e)).next,0===t){if(Object(n)!==n)return;c=!1}else for(;!(c=(r=a.call(n)).done)&&(i.push(r.value),i.length!==t);c=!0);}catch(e){l=!0,u=e}finally{try{if(!c&&null!=n.return&&(o=n.return(),Object(o)!==o))return}finally{if(l)throw u}}return i}}(e,t)||function(e,t){if(!e)return;if("string"==typeof e)return l(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);"Object"===n&&e.constructor&&(n=e.constructor.name);if("Map"===n||"Set"===n)return Array.from(e);if("Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n))return l(e,t)}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function l(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}var m=Object(o.h)((function(e){var t=localStorage.getItem("kuId"),n=[{name:"k8s信息",url:"/kubernetes/".concat(t,"/kuSetting/kuProject"),key:"kuSetting",encoded:"kuSetting"},{name:"成员",url:"/kubernetes/".concat(t,"/kuSetting/kuMember"),key:"kuMember",encoded:"kuMember"},{name:"权限",url:"/kubernetes/".concat(t,"/kuSetting/kuPermissions"),key:"permissions",encoded:"permissions"}],a=c(Object(r.useState)("/kubernetes/".concat(t,"/kuSetting/kuProject")),2),o=a[0],l=a[1];return Object(r.useEffect)((function(){l(e.location.pathname)}),[t]),u.a.createElement("div",{className:"kuSetting-box-right-left",__source:{fileName:i,lineNumber:43,columnNumber:9}},u.a.createElement("div",{className:"kuSetting-box-right-text title",__source:{fileName:i,lineNumber:44,columnNumber:13}},"设置"),u.a.createElement("div",{className:"kuSetting-box-menu",__source:{fileName:i,lineNumber:47,columnNumber:13}},n.map((function(t,n){return u.a.createElement("div",{key:n,onClick:function(){return n=t.url,l(n),void e.history.push(n);var n},className:"kuSetting-box-right-tabs ".concat(t.url===o?"kuSetting-kuSetting-select":""),__source:{fileName:i,lineNumber:51,columnNumber:29}},u.a.createElement("span",{__source:{fileName:i,lineNumber:56,columnNumber:33}},t.name))}))))})),s="D:\\code\\companycode\\xmonitor\\thoughtware-kaelthas-ui\\src\\k8s\\setting\\common\\KuSetting.js";t.default=Object(o.h)((function(e){var t=e.route;return u.a.createElement("div",{className:"ku-setting-layout",__source:{fileName:s,lineNumber:12,columnNumber:9}},u.a.createElement(m,{__source:{fileName:s,lineNumber:13,columnNumber:13}}),u.a.createElement("div",{className:"ku-setting-layout-right",__source:{fileName:s,lineNumber:14,columnNumber:13}},Object(a.a)(t.routes)))}))}}]);