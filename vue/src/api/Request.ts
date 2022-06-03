import axios from "axios";

class Request{
    public inst=axios.create({
        baseURL: "http://172.21.118.52:8081/api/",
        timeout: 1000,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            "Accept": "application/json",
            "Access-Control-Allow-Origin": "*",
        },
        withCredentials: true
    });
}

export default new Request();


