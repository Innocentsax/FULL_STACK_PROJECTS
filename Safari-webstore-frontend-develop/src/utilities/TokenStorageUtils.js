const saveToken = (token) => {
  if (!_checkBrowserSupportForLocalStorage) {
    _setCookie("token", token, 365);

    return;
  }

  if (getToken != null) window.localStorage.removeItem("token");

  try {
    window.localStorage.setItem("token", token);
  } catch (error) {
    return false;
  }

  return true;
};

const getToken = () => {
  if (!_checkBrowserSupportForLocalStorage) return _getCookie("token");

  return window.localStorage.getItem("token");
};

const _checkBrowserSupportForLocalStorage = () => {
  if (typeof Storage !== "undefined") return true;
  else return false;
};

function _setCookie(cname, cvalue, exdays) {
  const d = new Date();
  d.setTime(d.getTime() + exdays * 24 * 60 * 60 * 1000);
  let expires = "expires=" + d.toUTCString();
  document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function _getCookie(cname) {
  let name = cname + "=";
  let decodedCookie = decodeURIComponent(document.cookie);
  let ca = decodedCookie.split(";");
  for (let i = 0; i < ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) == " ") {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

export { saveToken, getToken };
