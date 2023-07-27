import { useState, useEffect } from "react";
import { motion } from "framer-motion";
import { Grid, Typography, Paper, Stack, Dialog, Accordion, AccordionSummary, AccordionDetails } from "@mui/material";
import { Skeleton } from "@mui/material";
import axios from "axios";
import Image from "../interfaces/Image";
import Submission from "../interfaces/Submission";


type GalleryProps = {
    submissions: Submission[]
}
export default function Gallery(props: GalleryProps) {
    const [value, setValue] = useState<Image>();
    const [open, setOpen] = useState(false);

    const toggleOpen = () => {
        setOpen(!open);
    };

    const onImageClick = (image: Image) => {
        setValue(image);
        setOpen(true);
    };


    return (
        <Paper sx={{ minHeight: '100vh' }}>
            <div className="gallery">
                {props.submissions.length == 0 && <Stack direction="column" spacing={1} justifyContent="center" alignItems="center">

                    <Typography variant="h1">No submissions yet</Typography>

                </Stack>}


                {props.submissions.map((image) => (
                    <motion.div className="pics"
                        whileHover={{ scale: 1.05 }}
                        transition={{ type: "spring", stiffness: 400, damping: 20 }}
                        onClick={() => onImageClick(image)}
                    >
                        <Stack direction="column" spacing={1} justifyContent="center" alignItems="center">

                            <img src={image.image} className="pic" />
                            <Typography variant="image_title">{image.title}</Typography>
                            <Typography>{image.width}x{image.height}</Typography>

                        </Stack>

                    </motion.div>
                ))}
            </div>


            <Dialog open={open} onClose={toggleOpen} maxWidth="lg" PaperProps={{
                style: {
                    backgroundColor: 'transparent',
                    backgroundImage: "unset",
                    boxShadow: 'none',
                },
            }}>
                <img src={value?.image} style={{ height: "100%", width: "100%", objectFit: "contain" }} onClick={toggleOpen} />
            </Dialog>
        </Paper >

    );
}