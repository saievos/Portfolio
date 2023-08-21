import axios from "axios";


export default {
    retrieveListNames() {
        return axios.get('/list/names');
    },
    retrieveGameLists(listName) {
        return axios.get('/list', { params: { listName } });
    },
    createCustomList(listName) {
        return axios.post('/list', { listName: listName });
    },
    deleteCustomList(listName) {
        return axios.delete('/list', { params: { name: listName } });
    },
    addGameToList(gameId, listName) {
        return axios.post('/game', { gameId: gameId, listName: listName });
    },
    deleteGame(gameId) {
        return axios.delete('/game', { params: { gameId }})
        .then((response) => response.data);
    },
    editList(listName, updateName) {
        return axios.put('/list', { listName: listName, updateName: updateName});
    },
    fetchVideos(gameId, token) {
        return axios.get('/videos', { params: { gameId, token}});
    }
}