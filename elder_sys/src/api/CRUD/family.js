import axios from "@/utils/request.js";

export const addFamily = {
    add: (form) => {
        const formData = new FormData();
        formData.append('name', form.name);
        formData.append('password', form.password);
        formData.append('auth', form.auth);
        formData.append('relationship', form.relationship);
        formData.append('elderly', form.elderly);
        formData.append('email', form.email);
        formData.append('phone', form.phone);
        return axios.post('/api/info/addfamily', formData,
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                timeout: 5000
            });
    },

};

export const editFamily = {
    edit: (form) => {
        const formData = new FormData();
        formData.append('id', form.id);
        formData.append('name', form.name);
        formData.append('password', form.password);
        formData.append('auth', form.auth);
        formData.append('relationship', form.relationship);
        formData.append('elderly', form.elderly);
        formData.append('email', form.email);
        formData.append('phone', form.phone);
        return axios.post('/api/info/editfamily', formData,
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                timeout: 5000
            });
    },

};

export const delFamily = {
    del: (id) => {
        return axios.post('/api/info/delfamily', {
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
    addFamily,
    editFamily,
    delFamily
};