import axios from "axios";

const baseURL = "https://api.rawg.io/api/games";
const queryParams = {
    key: "3aaebc6f1fde41139ff8dc2ba458719e",
    page_size: 120,
}

export default {
    onSearch(searchVal) {
        this.search = searchVal;
        return axios.get(baseURL, {params: queryParams});
    },
    fetchGames() {
        return axios.get(baseURL, {params: queryParams});
    },
    fetchGameDetails(id) {
        const url = `${baseURL}/${id}`;
        return axios.get(url, {params: queryParams});
    },
};