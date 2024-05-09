import axios from "@/utils/request.js";

export const addAdmin = {
    add: (form) => {
        const formData = new FormData();
        formData.append('name', form.name);
        formData.append('password', form.password);
        formData.append('auth', form.auth);
        return axios.post('/api/info/addadmin', formData,
            {
            headers: {
                'Content-Type': 'application/json'
            },
            timeout: 5000
        });
    },

};

export const editAdmin = {
    edit: (form) => {
        const formData = new FormData();
        formData.append('id', form.id);
        formData.append('name', form.name);
        formData.append('password', form.password);
        formData.append('auth', form.auth);
        return axios.post('/api/info/editadmin', formData,
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                timeout: 5000
            });
    },

};

export const delAdmin = {
    del: (id) => {
        return axios.post('/api/info/deladmin', {
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
    addAdmin,
    editAdmin,
    delAdmin
};