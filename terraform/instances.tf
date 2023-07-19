resource "aws_key_pair" "keypair" {
  public_key =file("~/.ssh/beerstore_rsa.pub")
}
resource "aws_instance" "instances" {
  count = 3
  ami = "ami-06ca3ca175f37dd66"
  instance_type = "t2.micro"

  subnet_id = element(aws_subnet.public_subnet.*.id, count.index)

  key_name = aws_key_pair.keypair.key_name

  vpc_security_group_ids = [aws_security_group.allow_ssh.id, aws_security_group.database.id]

  tags = {
    Name = "beerstore_instance_${count.index}"
  }
}

output "puclic_ips" {
  value = join(", ", aws_instance.instances.*.public_ip)
}