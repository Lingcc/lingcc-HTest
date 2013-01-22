package com.lingcc.hadoop.performance;


import java.util.jar.*;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.*;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.fs.FileUtil;

/** 
 * UnJar Performance Test.
 *  This is a testcase used for unjar performance test. 
 *   - Input: input.tar
 *   - Output: files and directories been extract from the jar file.
 *  The input file contains 18000+ files. which will cause lots of file lookup.
 *  And this will used to test two different types of unjar.
 *    - extract from local disk to local fs
 *    - extract from local dist to hdfs
 *
 *  Usage: java PerfunJarFS.java  XXX.jar FILE_PATH 
 *    - FILE_PATH can be /XXX/XXX/XXX, ./XXX/XXX/, or hdfs://XXXX:XXXX/XXX/XXXX
 *  Author: Ling Kun <lkun.lingcc@qq.com>
 */

// TODO:  Add time count inside the program.
// TODO: add local filesystem support

public class PerfunJarFS {
  /** Unpack a jar file into a directory. */
  public static void unJar(FileSystem fs, File jarFile, Path toDir) throws IOException {
    JarFile jar = new JarFile(jarFile);
    try {
      Enumeration entries = jar.entries();
      while (entries.hasMoreElements()) {
        JarEntry entry = (JarEntry)entries.nextElement();
        if (!entry.isDirectory()) {
          InputStream in = jar.getInputStream(entry);
          try{
            String dest = toDir.getName() + "/" + entry.getName();
            Path path = new Path(dest);

            FSDataOutputStream out=null;
            if(!fs.exists(path)) 
              out = fs.create(path);
            else
              out = fs.append(path);

            try {
              byte[] buffer = new byte[8192];
              int i;
              while((i = in.read(buffer)) != -1 ){
                out.write(buffer,0,i);
              }
            } finally {
              out.close();
            }
          } finally {
            in.close();
          }
        }
      }
    } finally {
      jar.close();
    }
  }

  public static void main(String[] args) throws Throwable {

    String usage = "PerfunJarFS jarFile UNJAR_PATH";

    if(args.length < 1 ){
      System.err.println(usage);
      System.exit(-1);
    }

    int firstArg = 0;
    String fileName = args[firstArg++];
    String destName = args[firstArg];
    
    Configuration config = new Configuration();
    hdfsURI="hdfs://iStore3:54310";
    LocalFSURI="file:///datapool/hadooptest/";
    FSURI=hdfsURI
    config.set("fs.default.name", hdfsURI );

    FileSystem fileSystem = FileSystem.get(config);
    File jarfile = new File(fileName);
    if(!(jarfile.exists())) {
      System.out.println("No Such source "+fileName);
      return;
    }
  
    destName = destName + "/hadoop-unjar2";
    Path destPath = new Path( destName );
    if(!fileSystem.exists(destPath))
      fileSystem.mkdirs(destPath);
    System.out.println("Unjar to Path:"+destPath.getName());

    Date startTime = new Date();
    System.out.println("Job started: " + startTime);
    unJar(fileSystem, jarfile, destPath);
    Date endTime = new Date();
    System.out.println("Job ended: " + endTime);
    fileSystem.close();

    System.out.println("UnJar Performance Test Done! The job took " +
                       (endTime.getTime() - startTime.getTime()) /1000 +
                       " seconds.");
  }
}
