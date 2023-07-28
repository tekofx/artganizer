import { useRouter } from 'next/router'
import { useEffect, useState, useContext } from 'react'
import Folder from '../../interfaces/Folder'
import Submission from '../../interfaces/Submission';
import { DataContext } from "../_app";
export default function Page() {
    const [folder, setFolder] = useState<Folder>();
    const [submissions, setSubmissions] = useState<Submission[]>([]);
    const [open, setOpen] = useState(false);
    const [color, setColor] = useState('#fff');
    const [textFieldError, setTextFieldError] = useState<boolean>(false);
    const [newFolder, setNewFolder] = useState<string>("New Name");
    const { data, setData } = useContext(DataContext);

    const router = useRouter()

    return (
        <div>{router.query.slug}</div>
    );
};