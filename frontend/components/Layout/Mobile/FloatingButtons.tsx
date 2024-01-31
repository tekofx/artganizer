import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import EditIcon from "@mui/icons-material/Edit";
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import { Fab, Stack } from "@mui/material";
import { motion } from "framer-motion";
import { useState } from "react";

interface FloatingButtonsProps {
    toggleEdit: () => void;
    toggleRemove: () => void;
}
export default function FloatingButtons({ toggleEdit, toggleRemove }: FloatingButtonsProps) {

    const [show, setShow] = useState(false);
    const variants = {
        open: { opacity: 1, y: 0, display: "block" },
        closed: { opacity: 0, y: "60px", display: "none" },
    };
    return (
        <Stack direction="column-reverse" spacing={2} sx={{ p: 4, position: "fixed", bottom: 60, right: 10 }}>

            <Fab color="primary" aria-label="edit" onClick={() => setShow(!show)} >
                <MoreHorizIcon />
            </Fab>
            <motion.div variants={variants}
                initial="closed"
                animate={show ? "open" : "closed"}
                transition={{ duration: 0.2 }}
            >
                <Fab color="primary" aria-label="edit" onClick={toggleEdit}>
                    <EditIcon />
                </Fab>
            </motion.div>
            <motion.div variants={variants}
                initial="closed"
                animate={show ? "open" : "closed"}
                transition={{ duration: 0.2 }}
            >
                <Fab color="primary" aria-label="delete" onClick={toggleRemove} >
                    <DeleteForeverIcon />
                </Fab>
            </motion.div>

        </Stack>
    )
}