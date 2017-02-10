package io.github.lahuman.reqbookapply;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Test;

public class ResourceFindText {

  @Test
  public void findResource(){
    ClassLoader classLoader = ResourceFindText.class.getClassLoader();
    System.out.println(classLoader);
    Assert.assertThat(classLoader, IsNull.notNullValue());
    System.out.println(classLoader.getResource("booklist.txt"));
    Assert.assertThat(classLoader.getResource("booklist.txt"), IsNull.notNullValue());
  }
}
