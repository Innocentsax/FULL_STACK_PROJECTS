import axios from "axios";

export const apiGet = (path, conf = {}, auth = "admin") => {
    const config = {
      ...conf,
      headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
      },
    };
    if (!auth) {
      config.headers = {};
    }
    return axios.get(`${process.env.REACT_APP_BASE_URL}${path}`, config);
  };
  export const apiPost = (path, data, { headers, ...conf }, auth = true)  => {
    const Authorization = auth && `Bearer ${localStorage.getItem("accessToken")}`;
    const config = {
      ...conf,
      headers: {
        Authorization,
        ...(headers ? headers : {}),
      },
    };
    return axios.post(`${process.env.REACT_APP_BASE_URL}${path}`, data, config);
  };
  export const apiPut = (path, data, conf = {}) => {
    const config = {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      ...conf,
    };
    return axios.put(`${process.env.REACT_APP_BASE_URL}${path}`, data, config);
  };
  export const apiPatch = (path, data, conf = {}) => {
    const config = {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      ...conf,
    };
    return axios.patch(`${process.env.REACT_APP_BASE_URL}${path}`, data, config);
  };
  export const apiDelete = (path, conf = {}) => {
    const config = {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      ...conf,
    };
    return axios.delete(`${process.env.REACT_APP_BASE_URL}${path}`, config);
  };