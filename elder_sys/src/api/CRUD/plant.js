import axios from "@/utils/request.js";

export const addPlant = {
    add: (form) => {
        const formData = new FormData();
        formData.append('name', form.name);
        formData.append('rfid', form.rfid);
        formData.append('description', form.description);
        return axios.post('/api/info/addplant', formData,
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                timeout: 5000
            });
    },

};

export const editPlant = {
    edit: (form) => {
        const formData = new FormData();
        formData.append('id', form.id);
        formData.append('name', form.name);
        formData.append('rfid', form.rfid);
        formData.append('description', form.description);
        return axios.post('/api/info/editplant', formData,
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                timeout: 5000
            });
    },

};

export const delPlant = {
    del: (id) => {
        return axios.post('/api/info/delplant', {
                id: id,
            },
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                timeout: 5000
            });
    },

};

export default {
    addPlant,
    editPlant,
    delPlant
};