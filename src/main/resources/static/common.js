// 公共请求封装：统一解析后端 R 结构，未登录自动跳转登录页
async function request(url, options) {
  options = options || {};
  options.headers = Object.assign({ 'Content-Type': 'application/json' }, options.headers || {});
  const resp = await fetch(url, options);
  const json = await resp.json();
  if (json.code === 0) {
    if (json.msg === 'NOTLOGIN') {
      const isAdmin = location.pathname.indexOf('/admin/') > -1;
      location.href = isAdmin ? '/admin/login.html' : '/user/login.html';
      return;
    }
    alert(json.msg || '操作失败');
    throw new Error(json.msg);
  }
  return json.data;
}

function post(url, body) {
  return request(url, { method: 'POST', body: JSON.stringify(body || {}) });
}
function put(url, body) {
  return request(url, { method: 'PUT', body: JSON.stringify(body || {}) });
}
function del(url) {
  return request(url, { method: 'DELETE' });
}

// 金额格式化
function fmtMoney(n) {
  return Number(n || 0).toFixed(2);
}
// 日期格式化
function fmtTime(t) {
  if (!t) return '';
  return String(t).replace('T', ' ').substring(0, 16);
}
