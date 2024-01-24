import axios from "axios";

const BACKEND_BASE_URL = "http://192.168.31.119:8080/api/";
const APP_VERSION = "v1";

class UserService {

    loginUser(user) {
        return axios.post(`${BACKEND_BASE_URL + APP_VERSION}/public/login`, user)
    }

    getUserFromToken(authToken) {
        return axios.get(`${BACKEND_BASE_URL + APP_VERSION}/startup`, {headers: {Authorization: `Bearer ${authToken}`}})
    }

    getUserFromUsername(authToken, username) {
        return axios.get(`${BACKEND_BASE_URL + APP_VERSION}/user/${username}`, {headers: {Authorization: `Bearer ${authToken}`}})
    }

    sseEvent(username) {
        return new EventSource(`${BACKEND_BASE_URL + APP_VERSION}/sse/connect/${username}`)
    }

    sendMessage(messageRequestDTO, authToken) {
        return axios.post(`${BACKEND_BASE_URL + APP_VERSION}/sse/user`, messageRequestDTO, {headers: {Authorization: `Bearer ${authToken}`}})
    }

    getUserConvo(userFrom, userTo, authToken) {
        return axios.get(`${BACKEND_BASE_URL + APP_VERSION}/message/user/${userFrom}-${userTo}`, {headers: {Authorization: `Bearer ${authToken}`}})
    }

    saveLatestMessage(userFromId, userToId, messageId, authToken) {
        return axios.get(`${BACKEND_BASE_URL + APP_VERSION}/latestmessage/${userFromId}-${userToId}-${messageId}`, {headers: {Authorization: `Bearer ${authToken}`}})
    }

    getUserInbox(userId) {
        return axios.
    }
}

const userService = new UserService();
export default userService;