import { useState } from "react";
import axios from "axios";

const useFetch = (url) => {

    const [data, setData] = useState("");
    const token = localStorage.getItem("token");

    async function getUserInfo(e) {
    try {
        const personInfoResponse = await axios.get(url, {
            headers: {Authorization: `Bearer ${token}`},
            params: {
            username: `${localStorage.getItem('username')}`
            }
        });
    
        setData(personInfoResponse.data);
    
        console.log(personInfoResponse.data);
        localStorage.setItem("person_info", personInfoResponse.data);
        // const details = personInfoResponse.data;
    
        console.log("here: " + data);
        return data;
    
    } catch (e) {
        console.log("User does not exist!");
    }
    }
    // useEffect(() => {
    //     fetch(url, {
    //       withCredentials: true,
    //       credentials: 'include',
    //       headers: {
    //         'Authorization': `Bearer ${token}`,
    //         'Connection': 'keep-alive',
    //         'Content-Type': 'application/json'
    //     },
    //       mode: "no-cors",
    //       method: "GET",
    //     }).then(res => res.json())
    //     .then(data => setData(data))
    //     .catch(err => console.log(err));
    // }, [token]);
    // return [data];
  };

  export default useFetch;
