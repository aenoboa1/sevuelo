import axios from "axios";
import { IRequest } from "../shared/request.model";

const apiUrl = "http://localhost:8080/api";

export const getAllRequests = () => {
  const requestUrl = `${apiUrl}/requests`;
  return axios.get<[IRequest]>(requestUrl);
};

export const getRequestById = (id: number) => {
  const requestUrl = `${apiUrl}/requests/${id}`;
  return axios.get<IRequest>(requestUrl);
};

export const updateStatusReserve = (request: IRequest) => {
  const requestUrl = `${apiUrl}/reserve`;
  return axios.put(requestUrl, request);
};
export const updateStatusNew = (request: IRequest) => {
  const requestUrl = `${apiUrl}/unreserve`;
  return axios.put(requestUrl, request);
};

export const deleteRequest = (id: number) => {
  const requestUrl = `${apiUrl}/delete/${id}`;
  return axios.delete(requestUrl);
};

export const createRequest = (request: IRequest) => {
  const requestUrl = `${apiUrl}/requests`;
  return axios.post(requestUrl, request);
};
