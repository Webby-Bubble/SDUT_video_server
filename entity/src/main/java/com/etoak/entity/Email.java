package com.etoak.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {

  /** 邮件主题 */
  private String subject;

  /** 收件人 */
  private String receiver;

  /** 邮件内容 */
  private String content;
}
