import { getToken } from "./TokenStorageUtils";

const setHeader = () => ({
  headers: {
    Authorization: `Bearer ${getToken()}`,
  },
});

export default setHeader;
