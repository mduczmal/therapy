import React, {useState} from 'react';
import { DataGrid } from '@material-ui/data-grid';
import Box from "@material-ui/core/Box";

export function Pricing(props) {
    const labels = {
        service: 'usługa',
        price: 'cena',
        delete: 'Usuń'
    }

    const columns = [
        { field: 'service', sortable: false, headerName: labels.service, width: 300 },
        { field: 'price', sortable: false, headerName: labels.price, width: 100, type: 'number' },
    ];

    const [rows, setRows] = useState([
        { id: 1, service: 'Pierwsza wizyta', price: 100 },
        { id: 2, service: 'Kolejna wizyta', price: 80 },
    ]);

    const handleDelete = () => {
        setRows([]);
    }

    return (
        <Box>
            <div style={{height: 400}} >
            <DataGrid rows={rows} columns={columns} pageSize={8}/>
            </div>
            {/*<Button type='text' onClick={handleDelete}><DeleteIcon></DeleteIcon>{labels.delete}</Button> */}
        </Box>
    );
}