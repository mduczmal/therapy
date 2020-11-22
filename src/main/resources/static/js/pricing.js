import React, {useState} from 'react';
import {DataGrid} from '@material-ui/data-grid';
import Box from "@material-ui/core/Box";
import Button from "@material-ui/core/Button";
import DeleteIcon from '@material-ui/icons/Delete';

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

    const[selectedRows, setSelected] = useState([]);

    const handleDelete = (event) => {
        setRows(
            rows.filter((r) => !selectedRows.includes(r.id))
        );
        console.log("clicked");
        event.preventDefault();
    }

    const handleSelectionChange = (event) => {
        setSelected(event.rowIds.map(s => parseInt(s)));
    }

    return (
        <Box>
            <Box style={{height: 400}} >
            <DataGrid
                checkboxSelection={true}
                onSelectionChange={handleSelectionChange}
                rows={rows}
                columns={columns}
                pageSize={8}
            />
            </Box>
            <Box>
                <Button type='button' onClick={handleDelete} startIcon={<DeleteIcon />}>{labels.delete}</Button>
            </Box>
        </Box>
    );
}