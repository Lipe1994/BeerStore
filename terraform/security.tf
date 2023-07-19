resource "aws_security_group" "allow_ssh" {
  name = "beerstore_ssh"
  vpc_id = aws_vpc.main.id
  description = "allow connection with ssh"

  ingress {
    from_port = "22"
    to_port = "22"
    protocol = "tcp"
    cidr_blocks = ["45.229.156.212/32"]
  }

  tags = {
    Name = "beerstore_security_group"
  }
}

resource "aws_security_group" "database" {
  name = "beerstore_database"
  vpc_id = aws_vpc.main.id

  ingress {
    from_port = "5432"
    to_port = "5432"
    protocol = "tcp"
    self = true
  }

  tags = {
    Name = "beerstore_database_security_group"
  }
}

resource "aws_db_subnet_group" "databases_group" {
  name = "beerstore_database_group"
  subnet_ids = flatten(chunklist(aws_subnet.private_subnet.*.id, 1))
  tags = {
    Name = "beerstore_database_group"
  }
}