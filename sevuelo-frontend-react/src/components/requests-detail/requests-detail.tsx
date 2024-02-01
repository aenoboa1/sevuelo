import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import {Button} from 'primereact/button';
import {useNavigate} from "react-router-dom";
import {getRequestById, updateStatusNew, updateStatusReserve} from '../../services/requests.service';
import {defaultValue} from '../../shared/request.model';
import '../../App.css';

export const RequestsDetail = () => {

    const [request, setRequest] = useState(defaultValue);

    const [Reserved, setReserved] = useState()

    const param = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        getRequestById(Number(param.id)).then(res => setRequest(res.data));
    }, [param.id])

    const reserve = () => {
        updateStatusReserve(request);
        window.history.back();
    }
    const unreserve = () => {
        updateStatusNew(request);
        window.history.back();
    }

    return (
        <>
            <div className="row justify-content-center">
                <div className="col-8">
                    {request && (
                        <>
                            <h2>Request Details</h2>
                            <div className='details'>
                                <div><span>Id: {request.id}</span></div>
                                <div><span>Passenger: {request.passenger}</span></div>
                                <div><span>Destination: {request.destination}</span></div>
                                <div><span>Status: {request.status}</span></div>
                                <div><span>Comments: {request.comments}</span></div>
                                <br/>
                                <Button
                                    className="p-button-secondary"
                                    onClick={() => navigate('/requests')}
                                    label="Back"/>{' '}

                                {request.status === 'RESERVED' ? (

                                    <Button onClick={unreserve} className="p-button-primary" label='New'/>
                                ) : (
                                    <Button onClick={reserve} className="p-button-primary" label='Reserve'/>

                                )
                                }
                            </div>
                        </>
                    )}
                </div>
            </div>
        </>
    );
}

export default RequestsDetail;
