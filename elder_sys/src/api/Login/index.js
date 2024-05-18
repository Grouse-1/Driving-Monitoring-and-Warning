import axios from "@/utils/request.js";

const ULogin = {
    login: (form) => {
        const formData = new FormData();
        formData.append('name', form.name);
        formData.append('password', form.password);
        formData.append('role', form.role);

        return axios.post('/api/login', formData, {
            headers: {
                'Content-Type': 'application/json'
            },
            timeout: 5000
        });
    },

};

export default ULogin;
