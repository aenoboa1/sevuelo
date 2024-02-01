export interface IRequest {
  id?: number;
  passenger?: string;
  destination?: string;
  status?: string;
  comments?: string;
}

export const defaultValue: IRequest = {};
