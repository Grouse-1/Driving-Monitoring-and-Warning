import axios from "@/utils/request.js";
import path from "@/api/path.js";


const GetMenu = {
    menu: () => {
        return axios.get('/api/menu', {
            headers: {
                'Content-Type': 'application/json'
            },
            timeout: 5000
        });
    },

};

export default GetMenu;