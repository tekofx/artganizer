import Cookies from "universal-cookie";
var cookies = new Cookies();

const AxiosOptions = {
  headers: { Authorization: `Bearer ${cookies.get("TOKEN")}` },
};

export default AxiosOptions;
