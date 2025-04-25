import axios from './axios';

export const getCrewMembers = async () => {
  const response = await axios.get('/crew');
  return response.data;
};