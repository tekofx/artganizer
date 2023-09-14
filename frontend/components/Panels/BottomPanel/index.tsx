import { Paper, Stack } from "@mui/material";
import { useContext } from "react";
import { DataContext } from "../../../pages/_app";
import { Submission } from "../../../interfaces";
import { useRouter } from "next/router";
interface BottomPanelProps {
  current: Submission;
}
export default function BottomPanel({ current }: BottomPanelProps) {
  const { data } = useContext(DataContext);
  const router = useRouter();

  return (
    <Paper>
      <Stack direction="row">
        {data.submissions.map((submission) => (
          <img
            src={submission.image}
            key={submission.id}
            height="150px"
            style={{
              border: submission.id == current.id ? "5px solid #555" : "none",
              cursor: "pointer",
            }}
            onClick={() => router.push(`/submission/${submission.id}`)}
          />
        ))}
      </Stack>
    </Paper>
  );
}
