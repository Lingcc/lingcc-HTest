#!/usr/bin/python3


TOOL_HOME="$HADOOP_HOME"

SOURCE_CMD="source ~/.bashrc"

def run_remote_cmd_list( user, host, cmd_lst):
  remote_cmd_str = " ; ".join(cmd_lst)
  login_info = user + "@" + host


