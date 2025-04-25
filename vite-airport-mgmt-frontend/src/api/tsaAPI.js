import axios from './axios';

export const getTSAPersonnel = async () => {
  const response = await axios.get('/tsa');
  return response.data;
};