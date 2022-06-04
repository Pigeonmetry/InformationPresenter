import axios from "axios";

class Request {
    public inst = axios.create({
        baseURL: "/api/",
        timeout: 3000,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            "Accept": "application/json",
            "Access-Control-Allow-Origin": "*",
        },
        withCredentials: true
    });
}

export default new Request();


