import React, {useEffect, useState} from 'react';
import {deleteRequest, getAllRequests} from '../../services/requests.service';
import {defaultValue} from '../../shared/request.model';
import {useNavigate} from 'react-router-dom';
import {Button} from 'primereact/button';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';

export const Requests = () => {

    const [requests, setRequests] = useState([defaultValue]);

    const navigate = useNavigate();

    useEffect(() => {
        getAllRequests().then((res) => setRequests(res.data));
    }, []);

    return (
        <>
            <h2>Requests</h2>
            {requests && (
                <div className="table-responsive">

                    <DataTable value={requests} responsiveLayout="scroll" emptyMessage={"No request found"}>
                        <Column header='Actions'
                                body={request => (
                                    <>
                                        <Button label='View' onClick={() => navigate(`/requests/${request.id}`)}/>
                                        <Button label='Delete' onClick={() => deleteRequest(request.id)}/>
                                    </>
                                )
                                }/>
                        <Column field='id' header='Id'/>
                        <Column field='passenger' header='Passenger'/>
                        <Column field='destination' header='Destination'/>
                        <Column field='status' header='Status'/>


                    </DataTable>


                </div>

            )}
        </>
    );
}

export default Requests;
