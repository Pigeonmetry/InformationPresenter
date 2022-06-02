import axios from "axios";

class Request{
    public inst=axios.create({
        baseURL: "http://localhost:8081/api/",
        timeout: 1000,
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        withCredentials: true
    });
}

export default new Request();


